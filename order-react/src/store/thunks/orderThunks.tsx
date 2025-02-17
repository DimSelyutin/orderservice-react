import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { makeRequest } from '../utils/axiosInstance';
import { Order, FetchOrderByTotalArgs } from '@/type/order';


export const fetchOrderById = createAsyncThunk<Order, number, { rejectValue: { message: string } }>(
    'orders/fetchOrderById',
    async (orderId, thunkAPI) => {
        return await makeRequest(
            {
                url: `orders/${orderId}`,
                method: 'get',
            },
            thunkAPI
        );
    }
);


export const fetchOrderByTotal = createAsyncThunk<Order, FetchOrderByTotalArgs, { rejectValue: { message: string } }>(
    'orders/fetchOrderByTotal',
    async ({ startDate, minTotalAmount }, thunkAPI) => {
        return await makeRequest(
            {
                url: `/api/v1/orders/date/${startDate}?total=${minTotalAmount}`, // Исправлено использование шаблонных литералов
                method: 'get',
            },
            thunkAPI
        );
    }
);

export const fetchOrders = createAsyncThunk<Order[],
    {
        orderId?: number
        minDate?: string;
        minTotalAmount?: number;
        excludedItem?: string;
        startDate?: string;
        endDate?: string;
    },
    { rejectValue: { message: string } }
>(
    'orders/fetchOrders',
    async (filters, thunkAPI) => {
        return await makeRequest(
            {
                url: 'orders',
                method: 'get',
                params: filters,
            },
            thunkAPI
        );
    }
);

// Асинхронное действие для создания заказа
export const createOrder = createAsyncThunk<Order, Order, { rejectValue: { message: string } }>(
    'orders/createOrder',
    async (newOrder, thunkAPI) => {
        return await makeRequest(
            {
                url: 'orders',
                method: 'post',
                data: newOrder,
            },
            thunkAPI
        );
    }
);