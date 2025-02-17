import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useActions } from '../hooks/useActions';
import { RootState } from '../store/store';

interface Order {
    id: number;
    customerName: string;
    status: string;
    totalAmount: number;
    date: string;
    items: string[];
}

const OrdersScreen: React.FC = () => {
    const { fetchOrders, fetchOrderById, fetchOrderByTotal } = useActions();

    const orders = useSelector((state: RootState) => state.order.orders) as unknown as Order[]; // Прямое указание типов
    const loading = useSelector((state: RootState) => state.order.status);
    const error = useSelector((state: RootState) => state.order.error);

    const [filterType, setFilterType] = useState<string>('');
    const [filters, setFilters] = useState<{
        orderId: number;
        minTotalAmount: number;
        excludedItem: string;
        startDate: string;
        endDate: string;
    }>({
        orderId: 0,
        minTotalAmount: 0,
        excludedItem: '',
        startDate: '',
        endDate: '',
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        setFilters((prev) => ({
            ...prev,
            [name]: name === 'orderId' || name === 'minTotalAmount' ? Number(value) : value,
        }));

        if (name === 'filterType') {
            setFilterType(value);
            setFilters((prev) => ({ ...prev, [value]: '' })); // Сброс фильтра при изменении типа фильтра
        }
    };

    const handleApplyFilters = () => {
        const { orderId, minTotalAmount, excludedItem, startDate, endDate } = filters;

        // Условное выполнение действий в зависимости от выбранного типа фильтра
        if (filterType === 'orderId') {
            fetchOrderById(orderId);
        } else if (filterType === 'minTotalAmount') {
            // Отправка минимальной суммы для получения заказов
            fetchOrderByTotal({
                startDate,
                minTotalAmount
            });
        } else {
            fetchOrders({
                excludedItem: filterType === 'excludedItem' ? excludedItem : undefined,
                startDate: filterType === 'dateRange' ? startDate : undefined,
                endDate: filterType === 'dateRange' ? endDate : undefined,
            });
        }
    };

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-4">Список Заказов</h1>

            <div className="mb-4">


                <div className="row g-3">
                    <div className="d-flex justify-content-between align-items-center">
                        <div className="col-md-12 ">
                            <div className="col-md-6 p-1">
                                <label className="form-label">Выберите фильтр</label>
                                <select name="filterType" className="form-select" value={filterType} onChange={handleChange}>
                                    <option value="">-- Выберите фильтр --</option>
                                    <option value="orderId">По ID заказа</option>
                                    <option value="minTotalAmount">По минимальной сумме</option>
                                    <option value="excludedItem">По исключаемому товару</option>

                                </select>
                            </div>
                            <button className="btn btn-primary" onClick={handleApplyFilters}>Применить фильтры</button>

                        </div>

                    </div>


                    {filterType === 'orderId' && (
                        <div className="col-md-4">
                            <label className="form-label">ID заказа</label>
                            <input
                                type="number"
                                name="orderId"
                                className="form-control"
                                value={filters.orderId}
                                onChange={handleChange}
                                placeholder="Введите ID заказа"
                            />
                        </div>
                    )}

                    {filterType === 'minTotalAmount' && (
                        <>
                            <div className="col-md-4">
                                <label className="form-label">Минимальная сумма</label>
                                <input
                                    type="number"
                                    name="minTotalAmount"
                                    className="form-control"
                                    value={filters.minTotalAmount}
                                    onChange={handleChange}
                                    placeholder="Введите минимальную сумму"
                                />
                            </div>
                            <div className="col-md-4">
                                <label className="form-label">Дата</label>
                                <input
                                    type="date"
                                    name="startDate"
                                    className="form-control"
                                    value={filters.startDate}
                                    onChange={handleChange}
                                />
                            </div>
                        </>
                    )}

                    {filterType === 'excludedItem' && (
                        <>
                            <div className="col-md-4">
                                <label className="form-label">Начальная дата</label>
                                <input
                                    type="date"
                                    name="startDate"
                                    className="form-control"
                                    value={filters.startDate}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="col-md-4">
                                <label className="form-label">Конечная дата</label>
                                <input
                                    type="date"
                                    name="endDate"
                                    className="form-control"
                                    value={filters.endDate}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="col-md-4">
                                <label className="form-label">Исключить товар</label>
                                <input
                                    type="text"
                                    name="excludedItem"
                                    className="form-control"
                                    value={filters.excludedItem}
                                    placeholder="Введите название товара"
                                />
                            </div></>
                    )}


                </div>
            </div>

            {loading && <p>Загрузка...</p>}
            {error && <p className="text-danger">{error}</p>}

            <div className="row">
                {orders.length > 0 ? (
                    orders.map(order => (
                        <div key={order.id} className="col-md-4 mb-4">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">Заказ #{order.id}</h5>
                                    <p className="card-text">Клиент: {order.customerName}</p>
                                    <p className="card-text">Статус: {order.status}</p>
                                    <p className="card-text">Сумма: {order.totalAmount} ₽</p>
                                    <p className="card-text">Дата: {new Date(order.date).toLocaleDateString()}</p>
                                    <button className="btn btn-primary" onClick={() => console.log(`Просмотр заказа ${order.id}`)}>
                                        Просмотреть
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>Нет доступных заказов.</p>
                )}
            </div>
        </div>
    );
};

export default OrdersScreen;

