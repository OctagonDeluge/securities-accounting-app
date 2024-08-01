import {API_URL} from "../../constants/API";
import {axios} from "../axios";
import {showNotification} from "@mantine/notifications";
import {IconAlertCircle, IconCircleCheck, IconCircleX} from "@tabler/icons";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";

export function useSigninRequest() {
    const [data, setData] = useState({
        level: "",
        name: "",
        refreshToken: "",
        username: "",
        wallet: {
            id: 0,
            name: "",
            currency: "",
            balance: 0,
        }
    });

    const send = (body) => {
        axios
            .post(`${API_URL}/auth/signin`, body)
            .then((res) => {
                setData(res.data);
                setCredentials(res.headers["authorization"].substring(7), res.data.refreshToken);
            })
            .catch(reason => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: "Пользователь с данным email не зарегистрирован",
                    color: 'red',
                    icon: <IconCircleX/>,
                });
            })
    }

    return {data, send};
}

export function useSignupRequest(signupRequestData) {
    const navigate = useNavigate();
    const request = () => {
        if (signupRequestData.name === "" || signupRequestData.email === "" || signupRequestData.password === "") {
            showNotification({
                autoClose: 5000, title: "Ошибка", message: "Заполните все поля", color: 'red', icon: <IconAlertCircle/>,
            });
        } else {
            axios
                .post(`${API_URL}/auth/signup`, signupRequestData)
                .then((res) => {
                    showNotification({
                        autoClose: 5000,
                        title: "Успех",
                        message: "Пользователь зарегистрирован",
                        color: 'green',
                        icon: <IconCircleCheck/>,
                    })
                    navigate("/signin");
                })
                .catch((reason) => {
                    console.log(reason.response)
                    showNotification({
                        autoClose: 5000,
                        title: "Ошибка",
                        message: reason.response.data.message,
                        color: 'red',
                        icon: <IconCircleX/>,
                    })
                });
        }
    }

    return {request};
}

export function useSignoutRequest() {
    const navigate = useNavigate();

    const request = () => {
        localStorage.clear();
            axios
            .post(`${API_URL}/auth/signout`)
            .then(() => {
                navigate("/signin");
            });
    }

    return {request};
}

export function sendRefreshToken() {
    const refreshTokenRequest = {
        refreshToken: localStorage.getItem("refresh")
    }
    return axios
        .post(`${API_URL}/auth/refresh`, refreshTokenRequest)
        .then(res => res);
}

const setCredentials = (accessToken, refreshToken) => {
    localStorage.setItem("Bearer", accessToken);
    localStorage.setItem("refresh", refreshToken);
}