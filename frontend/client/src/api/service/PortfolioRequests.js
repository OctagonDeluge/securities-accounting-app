import {axios} from "../axios";
import {API_URL} from "../../constants/API";
import {showNotification} from "@mantine/notifications";
import {IconCircleCheck} from "@tabler/icons";
import React, {useEffect, useState} from "react";

const PAGE_SIZE = 10;

export function useGetPortfolios() {
    const [portfolios, setPortfolios] = useState([])
    const [loading, setLoading] = useState(true)
    const [hasMore, setHasMore] = useState(false)
    const [error, setError] = useState("");

    const request = async (page) => {
        setLoading(true);
        try {
            const result = await axios
                .get(`${API_URL}/portfolio`, {
                    params: {
                        page: page,
                        size: PAGE_SIZE
                    }
                });
            setPortfolios(result.data);
            setHasMore(result.data.length > 0);
        } catch (err) {
            setError(err.message || "Unexpected Error!");
        } finally {
            setLoading(false);
        }
    };

    return {request, loading, portfolios, hasMore};
}

export async function postPortfolio(name) {
    return await axios
        .post(`${API_URL}/portfolio`, {}, {
            params: {
                portfolioName: name
            }
        })
        .then(response => response.data)
        .catch(res => {
            let text = "";
            res.response.data.map(resp => text += resp.fieldName + " " + resp.message);
            showNotification({
                title: "Не удалось добавить портфель",
                message: text,
                color: "red"
            })
        });
}

export async function deletePortfolio(id) {
    return await axios
        .delete(`${API_URL}/portfolio/${id}`)
        .then(() => {
            showNotification({
                autoClose: 5000,
                title: "Успех",
                message: "Портфель удален",
                color: "green",
                icon: <IconCircleCheck/>,
            })
        })
        .catch(res => {
            let text = "";
            res.response.data.map(resp => text += resp.fieldName + " " + resp.message);
            showNotification({
                title: "Не удалось удалить портфель",
                message: text,
                color: "red"
            })
        })
}

export async function putPortfolio(id, name) {
    return await axios
        .put(`${API_URL}/portfolio/${id}`, {}, {
            params: {
                portfolioName: name
            }
        })
        .then(response => response.data)
        .catch(response => {
            let text = "";
            response.response.data.map(resp => text += resp.fieldName + " " + resp.message);
            showNotification({
                title: "Не удалось обновить портфель",
                message: text,
                color: "red"
            })
        })
}

