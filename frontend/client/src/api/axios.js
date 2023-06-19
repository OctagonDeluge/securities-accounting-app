import Axios, { AxiosRequestConfig } from "axios";
import {API_URL} from "../constants/API";
import {sendRefreshToken} from "./service/AuthRequests";

export const axios = Axios.create({
    baseURL: API_URL,
});

function authRequestInterceptor(config: AxiosRequestConfig) {
    config.headers.authorization = localStorage.getItem("Bearer");
    return config;
}

axios.interceptors.request.use(authRequestInterceptor);

axios.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        if (error.response) {
            if (error.response.status === 401) {
                axios.interceptors.request.eject(0);
                try {
                    const rs = await sendRefreshToken();
                    localStorage.setItem("refresh", rs.data);
                    localStorage.setItem("Bearer", rs.headers["authorization"].substring(7))
                    axios.interceptors.request.use(authRequestInterceptor);
                    return axios(error.config);
                } catch (_error) {
                    if (_error.response && _error.response.data) {
                        return Promise.reject(_error.response.data);
                    }
                }
            }
        }

        return Promise.reject(error);
    }
);