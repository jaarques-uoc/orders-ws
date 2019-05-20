package com.jaarquesuoc.shop.orders.services;

import com.jaarquesuoc.shop.orders.dtos.CustomerDto;
import com.jaarquesuoc.shop.orders.dtos.NextOrderIdDto;
import com.jaarquesuoc.shop.orders.dtos.OrderDto;
import com.jaarquesuoc.shop.orders.dtos.OrderEventDto;
import com.jaarquesuoc.shop.orders.dtos.OrderItemDto;
import com.jaarquesuoc.shop.orders.dtos.OrderStatus;
import com.jaarquesuoc.shop.orders.dtos.ProductDto;
import com.jaarquesuoc.shop.orders.mappers.OrderMapper;
import com.jaarquesuoc.shop.orders.models.Order;
import com.jaarquesuoc.shop.orders.models.OrderEvent;
import com.jaarquesuoc.shop.orders.repositories.OrderEventsRepository;
import com.jaarquesuoc.shop.orders.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jaarquesuoc.shop.orders.dtos.OrderStatus.CHECKOUT;
import static com.jaarquesuoc.shop.orders.dtos.OrderStatus.PAYMENT_PROCESSED;
import static com.jaarquesuoc.shop.orders.dtos.OrderStatus.SENT;
import static com.jaarquesuoc.shop.orders.dtos.OrderStatus.SHIPMENT_READY;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersService {

    private final ProductsService productsService;

    private final CustomersService customersService;

    private final MockedProcessesService mockedProcessesService;

    private final OrdersRepository ordersRepository;

    private final OrderEventsRepository orderEventsRepository;

    public Optional<OrderDto> getOrderDto(final String id) {
        Optional<OrderDto> optionalOrderDto = ordersRepository.findById(id)
            .map(OrderMapper.INSTANCE::toOrderDto);

        optionalOrderDto.ifPresent(this::populateOrderDtoWithProducts);

        return optionalOrderDto;
    }

    public List<OrderDto> getAllOrderDtos() {
        return ordersRepository.findAll()
            .stream()
            .map(OrderMapper.INSTANCE::toOrderDto)
            .collect(toList());
    }

    public List<OrderEventDto> getAllOrderEventDtosByOrderId(final String orderId) {
        return orderEventsRepository.findAllByOrderId(orderId)
            .stream()
            .map(OrderMapper.INSTANCE::toOrderEventDto)
            .collect(toList());
    }

    public List<OrderDto> getAllCustomerOrderDtos(final String customerId) {
        return ordersRepository.findAllByCustomerId(customerId)
            .stream()
            .map(OrderMapper.INSTANCE::toOrderDto)
            .collect(toList());
    }

    public Optional<OrderDto> getCustomerOrderDto(final String orderId, final String customerId) {
        Optional<OrderDto> optionalOrderDto = ordersRepository.findByIdAndCustomerId(orderId, customerId)
            .map(OrderMapper.INSTANCE::toOrderDto);

        optionalOrderDto.ifPresent(this::populateOrderDtoWithProducts);

        return optionalOrderDto;
    }

    public NextOrderIdDto getNextOrderId(final String customerId) {
        final Long nOrders = ordersRepository.countByCustomerId(customerId);

        return NextOrderIdDto.builder()
            .nextOrderId(generateNextOrderId(customerId, nOrders))
            .build();
    }

    public OrderDto createOrder(final OrderDto orderDto, final String customerId) {
        CustomerDto customerDto = customersService.getCustomerDto(customerId);

        return createOrder(orderDto, customerDto);
    }

    private OrderDto createOrder(final OrderDto orderDto, final CustomerDto customerDto) {
        populateWithCustomerDto(orderDto, customerDto);

        OrderDto createdOrderDto = createOrder(orderDto);

        populateOrderDtoWithProducts(createdOrderDto);

        return createdOrderDto;
    }

    private OrderDto createOrder(final OrderDto orderDto) {
        Order order = OrderMapper.INSTANCE.toOrder(orderDto);

        Order createdOrder = ordersRepository.save(order);

        createOrderEvent(createdOrder, CHECKOUT);

        processPayment(createdOrder);

        return OrderMapper.INSTANCE.toOrderDto(createdOrder);
    }

    private void createOrderEvent(final Order order, final OrderStatus orderStatus) {
        OrderEvent orderEvent = OrderEvent.builder()
            .orderId(order.getId())
            .status(orderStatus)
            .build();

        orderEventsRepository.save(orderEvent);
    }

    private void processPayment(final Order order) {
        Mono.fromCallable(mockedProcessesService::processPayment)
            .doOnNext(paymentSuccess -> createOrderEvent(order, PAYMENT_PROCESSED))
            .doOnNext(orderEvent1 -> mockedProcessesService.prepareShipment())
            .doOnNext(paymentSuccess -> createOrderEvent(order, SHIPMENT_READY))
            .doOnNext(orderEvent1 -> mockedProcessesService.sendOrder())
            .doOnNext(paymentSuccess -> createOrderEvent(order, SENT))
            .subscribeOn(Schedulers.parallel())
            .subscribe();
    }

    public void cleanDb() {
        ordersRepository.deleteAll();
    }

    private Optional<BigDecimal> calculateAmount(final OrderDto orderDto) {
        return orderDto.getOrderItemDtos().stream()
            .map(this::calculateItemAmount)
            .reduce(BigDecimal::add);
    }

    private BigDecimal calculateItemAmount(final OrderItemDto orderItemDto) {
        return orderItemDto.getProductDto().getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity()));
    }

    private String generateNextOrderId(final String customerId, final Long nOrders) {
        return customerId + "-" + nOrders;
    }

    private void populateWithCustomerDto(final OrderDto orderDto, final CustomerDto customerDto) {
        orderDto.setId(getNextOrderId(customerDto.getId()).getNextOrderId());
        orderDto.setCustomerId(customerDto.getId());
        orderDto.setAmount(calculateAmount(orderDto).orElse(ZERO));
    }

    private void populateOrderDtoWithProducts(final OrderDto orderDto) {
        if (orderDto.getOrderItemDtos() == null) {
            return;
        }

        List<String> productIds = orderDto.getOrderItemDtos().stream()
            .map(OrderItemDto::getProductDto)
            .map(ProductDto::getId)
            .collect(toList());

        Map<String, ProductDto> productDtos = productsService.getProducts(productIds);

        orderDto.getOrderItemDtos()
            .forEach(orderItemDto -> orderItemDto.setProductDto(productDtos.get(orderItemDto.getProductDto().getId())));
    }
}
