import React, {useCallback, useEffect, useRef, useState} from "react";
import "../assets/styles/PortfoliosStyle.css"
import "../assets/styles/PortfolioCardStyles.css"
import {PortfolioService} from "../api/service/PortfolioRequests";
import {PortfolioCard} from "../components/cards/PortfolioCard";
import {PortfolioAddCard} from "../components/cards/PortfolioAddCard";

export function Portfolio() {
    const [page, setPage] = useState(0);
    const service= PortfolioService();

    useEffect(() => {
        const d = setTimeout(() => {
            service.getPortfolios(page);
        }, 500);
    }, [page])

    const observer = useRef();

    const lastPortfolioRef = useCallback(el => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting && service.hasMore) {
                setPage(prevState => prevState + 1);
            }
        })
        if (el) observer.current.observe(el)
    }, [service.loading, service.hasMore]);


    return (
        <div className="portfolioMain">
            <PortfolioAddCard createPortfolio={service.postPortfolio}/>
            <div className="portfolios">
                {service.portfolios.map(portfolio => (
                    <PortfolioCard portfolio={portfolio}
                                   key={portfolio.id}
                                   updatePortfolio={service.updatePortfolio}
                                   deletePortfolio={service.deletePortfolio}/>
                ))}
            </div>
        </div>
    )
}