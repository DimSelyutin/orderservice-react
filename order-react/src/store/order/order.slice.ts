import { Order } from '@/type/order';
import { createSlice } from '@reduxjs/toolkit';
import {fetchOrders,createOrder,fetchOrderById} from '../thunks/orderThunks';


// Определение состояния заказа
interface OrderState {
    orders: Order[];
    status: 'idle' | 'loading' | 'succeeded' | 'failed';
    error: string | null;
    createStatus: 'idle' | 'loading' | 'succeeded' | 'failed';
    createError: string | null;
    selectedOrder: Order | null; // новое поле для хранения выбранного заказа
    fetchStatus: 'idle' | 'loading' | 'succeeded' | 'failed'; // новое поле для статуса получения заказа
    fetchError: string | null; // новое поле для ошибки получения заказа
}

const initialState: OrderState = {
    orders: [],
    status: 'idle',
    error: null,
    createStatus: 'idle',
    createError: null,
    selectedOrder: null,
    fetchStatus: 'idle',
    fetchError: null,
};
const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
        .addCase(fetchOrders.pending, (state) => {
            state.status = 'loading';
        })
        .addCase(fetchOrders.fulfilled, (state, action) => {
            state.orders = action.payload;
            state.status = 'succeeded';
            state.error = null;
        })
        .addCase(fetchOrders.rejected, (state, action) => {
            state.status = 'failed';
            state.error = action.payload?.message || 'Ошибка при загрузке заказов';
        })
        .addCase(fetchOrderById.pending, (state) => {
            state.fetchStatus = 'loading';
            state.selectedOrder = null; // обнуляем выбранный заказ перед загрузкой
        })
        .addCase(fetchOrderById.fulfilled, (state, action) => {
            state.selectedOrder = action.payload; // сохраняем полученный заказ
            state.fetchStatus = 'succeeded';
            state.fetchError = null;
        })
        .addCase(fetchOrderById.rejected, (state, action) => {
            state.fetchStatus = 'failed';
            state.fetchError = action.payload?.message || 'Ошибка при получении заказа';
        })
        .addCase(createOrder.pending, (state) => {
            state.createStatus = 'loading';
        })
        .addCase(createOrder.fulfilled, (state, action) => {
            state.orders.push(action.payload); // Добавление нового заказа в массив
            state.createStatus = 'succeeded';
            state.createError = null;
        })
        .addCase(createOrder.rejected, (state, action) => {
            state.createStatus = 'failed';
            state.createError = action.payload?.message || 'Ошибка при создании заказа';
        });
},
});

export default orderSlice.reducer;