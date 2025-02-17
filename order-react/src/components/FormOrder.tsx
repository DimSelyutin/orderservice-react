import React, { useState } from 'react';
import { Order, OrderDetail } from './../type/order';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useActions } from '../hooks/useActions';


const CreateOrderPage: React.FC = () => {
    const { createOrder } = useActions();

    const [order, setOrder] = useState<Order>({
        id: 0,
        orderNumber: '',
        totalAmount: 0,
        orderDate: '',
        recipient: '',
        deliveryAddress: '',
        paymentType: 'карта',
        deliveryType: 'самовывоз',
        orderDetails: [],
    });

    const [orderDetail, setOrderDetail] = useState<OrderDetail>({
        id: 0,
        productCode: 0,
        productName: '',
        quantity: 0,
        unitPrice: 0,
        orderId: 0, // будет обновлен при отправке заказа
    });

    const handleOrderChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setOrder({ ...order, [name]: value });
    };

    const handleOrderDetailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setOrderDetail({ ...orderDetail, [name]: value });
    };

    const addOrderDetail = () => {
        setOrderDetail({ ...orderDetail, id: order.orderDetails.length + 1, orderId: order.id });
        setOrder({ ...order, orderDetails: [...order.orderDetails, { ...orderDetail, orderId: order.id }] });
        setOrderDetail({ id: 0, productCode: 0, productName: '', quantity: 0, unitPrice: 0, orderId: 0 });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        createOrder(order);
    };

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-4">Создание Заказа</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="orderNumber">Номер заказа</label>
                    <input type="text" className="form-control" id="orderNumber" name="orderNumber" value={order.orderNumber} disabled placeholder="Выдается автоматически" />
                </div>
                <div className="form-group">
                    <label htmlFor="totalAmount">Общая сумма заказа</label>

                    <input type="number" className="form-control" id="totalAmount" name="totalAmount" value={order.totalAmount} onChange={handleOrderChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="orderDate">Дата заказа</label>
                    <input type="date" className="form-control" id="orderDate" name="orderDate" value={order.orderDate} onChange={handleOrderChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="recipient">Получатель</label>
                    <input type="text" className="form-control" id="recipient" name="recipient" value={order.recipient} onChange={handleOrderChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="deliveryAddress">Адрес доставки</label>
                    <input type="text" className="form-control" id="deliveryAddress" name="deliveryAddress" value={order.deliveryAddress} onChange={handleOrderChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="paymentType">Тип оплаты</label>
                    <select className="form-control" id="paymentType" name="paymentType" value={order.paymentType} onChange={handleOrderChange}>
                        <option value="карта">Карта</option>
                        <option value="наличные">Наличные</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="deliveryType">Тип доставки</label>
                    <select className="form-control" id="deliveryType" name="deliveryType" value={order.deliveryType} onChange={handleOrderChange}>
                        <option value="самовывоз">Самовывоз</option>
                        <option value="доставка до двери">Доставка до двери</option>
                    </select>
                </div>

                <h5 className="mt-4">Детали заказа</h5>
                <div className="form-group">
                    <label htmlFor="productCode">Артикул товара</label>
                    <input type="number" className="form-control" id="productCode" name="productCode" value={orderDetail.productCode} onChange={handleOrderDetailChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="productName">Название товара</label>
                    <input type="text" className="form-control" id="productName" name="productName" value={orderDetail.productName} onChange={handleOrderDetailChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="quantity">Количество</label>
                    <input type="number" className="form-control" id="quantity" name="quantity" value={orderDetail.quantity} onChange={handleOrderDetailChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="unitPrice">Стоимость единицы товара</label>
                    <input type="number" className="form-control" id="unitPrice" name="unitPrice" value={orderDetail.unitPrice} onChange={handleOrderDetailChange} required />
                </div>

                <button type="button" className="btn btn-secondary" onClick={addOrderDetail}>
                    Добавить деталь заказа
                </button>

                <h5 className="mt-4">Добавленные детали заказа</h5>
                <ul className="list-group">
                    {order.orderDetails.map((detail) => (
                        <li className="list-group-item" key={detail.id}>
                            {detail.productName} (Артикул: {detail.productCode}, Количество: {detail.quantity}, Стоимость: ${detail.unitPrice.toFixed(2)})
                        </li>
                    ))}
                </ul>

                <button type="submit" className="btn btn-primary mt-4">Создать заказ</button>
            </form>
        </div>
    );
};

export default CreateOrderPage;
