import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { setErrorMessage, setSuccessMessage } from './appMessages.slice';

// Убедитесь, что тип переменной apiUrl определен
export const apiUrl: string | undefined = process.env.REACT_APP_API_URL;

// Создаем экземпляр axios с базовым URL
export const axiosInstance = axios.create({
    baseURL: apiUrl,
});

// Интерцепторы ответа
axiosInstance.interceptors.response.use(
    (response: AxiosResponse) => {
        return response;
    },
    (error) => {
        if (error.response?.data?.message) {
            setErrorMessage(error.response.data.message);
        }

        return Promise.reject(error);
    }
);

// Определяем типы для параметров makeRequest
interface MakeRequestConfig {
    url: string;
    method?: 'get' | 'post' | 'put' | 'delete';
    data?: any;
    params?: any;
    isFormData?: boolean;
}
export const makeRequest = async (
    { url, method = 'get', data = null, params = null }: MakeRequestConfig,
    thunkAPI: any
) => {
    try {
        const config = { url, method, data, params };
        const response = await axiosInstance(config);

        // Выводим сообщение об успехе для методов, поддерживающих это
        if (['post', 'put', 'delete'].includes(method)) {
            const successMessage = response.data?.message;
            if (successMessage) {
                thunkAPI.dispatch(setSuccessMessage(successMessage));
            }
        }

        return response.data; 
    } catch (error) {
        const errorMessage = axios.isAxiosError(error) && error.response?.data?.message
            ? error.response.data.message
            : 'Произошла ошибка.';

        thunkAPI.dispatch(setErrorMessage(errorMessage));
        return thunkAPI.rejectWithValue({ message: errorMessage });
    }
};
