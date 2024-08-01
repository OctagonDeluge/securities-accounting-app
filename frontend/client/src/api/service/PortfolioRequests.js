import {axios} from "../axios";
import {API_URL} from "../../constants/API";
import {showNotification} from "@mantine/notifications";
import {IconCircleCheck, IconCircleX} from "@tabler/icons";
import React, {useState} from "react";

const PAGE_SIZE = 9;

export function PortfolioService() {
    const [portfolios, setPortfolios] = useState([]);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);

    const getPortfolios = (page) => {
        axios
            .get(`${API_URL}/portfolio`, {
                params: {
                    page: page,
                    size: PAGE_SIZE
                }
            })
            .then(response => {
                if(response.data.length > 0) {
                    setPortfolios([...portfolios, ...response.data]);
                } else {
                    setHasMore(false);
                }
                setLoading(false);
            })
    };

    const postPortfolio = (name) => {
        axios
            .post(`${API_URL}/portfolio`, {}, {
                params: {
                    portfolioName: name
                }
            })
            .then(newPortfolio => {
                setPortfolios([newPortfolio.data, ...portfolios]);
            })
    }

    const updatePortfolio = (portfolio, name) => {
        axios
            .put(`${API_URL}/portfolio/${portfolio.id}`, {}, {
                params: {
                    portfolioName: name
                }
            })
            .then((response) => {
                let clone = [...portfolios].filter(item => item.id !== portfolio.id);
                clone.unshift(response.data);
                setPortfolios(clone);
            })
            .catch(() => {
                let clone = portfolios;
                clone.shift();
                clone.unshift(portfolio)
                setPortfolios(clone);
            });
    }

    const deletePortfolio = (id) => {
        axios
            .delete(`${API_URL}/portfolio/${id}`)
            .then(() => {
                let clone = [...portfolios].filter(item => item.id !== id);
                setPortfolios(clone);
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: "Портфель удален",
                    color: "green",
                    icon: <IconCircleCheck/>,
                })
            })
            .catch(reason => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: "Портфель не пустой",
                    color: "red",
                    icon: <IconCircleX/>,
                })
            })
    }

    return {getPortfolios, postPortfolio, updatePortfolio, deletePortfolio, portfolios, loading, hasMore};
}




