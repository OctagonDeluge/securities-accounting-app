import React, {useEffect, useState} from "react";
import "../assets/styles/PortfoliosStyle.css"
import "../assets/styles/PortfolioCardStyles.css"
import {PortfolioService} from "../api/service/PortfolioRequests";
import {PortfolioCard} from "../components/cards/PortfolioCard";
import {PortfolioAddCard} from "../components/cards/PortfolioAddCard";

export function Portfolio() {
    const [page, setPage] = useState(0);
    const service = PortfolioService();

    useEffect(() => {
            service.getPortfolios(page);
    }, [page])

    const handleScroll = (e) => {
        const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
        if (bottom) {
            setPage((prevState) => prevState+1);
        }
    }

    return (
        <div className="portfolioMain">
            <PortfolioAddCard createPortfolio={service.postPortfolio}/>
                <div className="portfolios" onScroll={handleScroll}>
                    {service.portfolios.map((portfolio, index) => (
                        <PortfolioCard portfolio={portfolio}
                                       key={portfolio.id}
                                       updatePortfolio={service.updatePortfolio}
                                       deletePortfolio={service.deletePortfolio}/>
                    ))}
                </div>
            {service.loading ? <h4>Загрузка</h4> : ""}
        </div>
    )
}