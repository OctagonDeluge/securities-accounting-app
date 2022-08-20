import './App.css';
import {Route, Routes} from "react-router-dom";
import {Portfolio} from "./pages/Portfolio";
import {Security} from "./pages/Security";
import {Exchange} from "./pages/Exchange";
import {ExchangeSecurityInfo} from "./pages/ExchangeSecurityInfo";
import {HeaderMiddle} from "./components/headers/HeaderMiddle";
import React from "react";
import {links} from "./constants/HeaderLinks";
import {PortfolioSecurityInfo} from "./pages/PortfolioSecurityInfo";
import {Statistics} from "./pages/Statistics";

export function App() {
    return (
        <div className="App">
            <HeaderMiddle links={links}/>
            <Routes>
                <Route path="/portfolio" element={<Portfolio/>} />
                <Route path="/portfolio/:portfolioId/exchange" element={<Exchange/>} />
                <Route path="/portfolio/:portfolioId/security" element={<Security/>} />
                <Route path="/portfolio/:portfolioId/statistics" element={<Statistics/>} />
                <Route path="/portfolio/:portfolioId/security/:securityId" element={<PortfolioSecurityInfo/>} />
                <Route path="/portfolio/:portfolioId/exchange/:exchange/security/:secid" element={<ExchangeSecurityInfo/>} />
            </Routes>
        </div>
    );
}
