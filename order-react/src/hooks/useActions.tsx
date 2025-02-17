import { useMemo } from 'react';
import { bindActionCreators } from '@reduxjs/toolkit';
import { useDispatch } from 'react-redux';
import { createOrder, fetchOrders, fetchOrderById, fetchOrderByTotal } from './../store/thunks/orderThunks';

const rootActions = {
    fetchOrders, createOrder, fetchOrderById, fetchOrderByTotal
}

export const useActions = () => {
    const dispatch = useDispatch();

    return useMemo(() => bindActionCreators(rootActions, dispatch), [dispatch]);
};
