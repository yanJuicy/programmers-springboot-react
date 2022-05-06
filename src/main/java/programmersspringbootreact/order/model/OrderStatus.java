package programmersspringbootreact.order.model;

public enum OrderStatus {
    ACCEPTED,   // 주문 확인
    READY_FOR_DELIVERY, // 배송 준비 (오후 2시 이후)
    SHIPPED,    // 배송 완료
}
