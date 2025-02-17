export interface Order {
    id: number;
    orderNumber: string;
    totalAmount: number;
    orderDate: string;
    recipient: string;
    deliveryAddress: string;
    paymentType: 'карта' | 'наличные';
    deliveryType: 'самовывоз' | 'доставка до двери';
    orderDetails: OrderDetail[];
}

export interface OrderDetail {
    id: number;
    productCode: number;
    productName: string;
    quantity: number;
    unitPrice: number;
    orderId: number;
}
export interface FetchOrderByTotalArgs {
    startDate: string;
    minTotalAmount: number;
}