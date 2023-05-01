import './App.css';
import {Route, Routes} from "react-router-dom";
import {Portfolio} from "./pages/Portfolio";
import {Security} from "./pages/Security";
import {Exchange} from "./pages/Exchange";
import {ExchangeSecurityInfo} from "./pages/ExchangeSecurityInfo";
import {HeaderMiddle} from "./components/headers/HeaderMiddle";
import React, {useState} from "react";
import {links} from "./constants/HeaderLinks";
import {PortfolioSecurityInfo} from "./pages/PortfolioSecurityInfo";
import {Statistics} from "./pages/Statistics";
import {NotFound} from "./pages/NotFound";
import {Signup} from "./pages/Signup";
import {Signin} from "./pages/Signin";
import {useWalletService} from "./api/service/WalletService";
import {useAccountService} from "./api/service/AccountService";

export function App() {
    const walletService = useWalletService();
    const accountService = useAccountService();
    return (
        <div className="App">
            {accountService.auth ?
                <>
                    <HeaderMiddle service={walletService} links={links}/>
                    <Routes>
                        <Route path="/portfolio" element={<Portfolio/>} />
                        <Route path="/portfolio/:portfolioId/exchange" element={<Exchange/>} />
                        <Route path="/portfolio/:portfolioId/security" element={<Security/>} />
                        <Route path="/portfolio/:portfolioId/statistics" element={<Statistics/>} />
                        <Route path="/portfolio/:portfolioId/security/:securityId" element={<PortfolioSecurityInfo/>} />
                        <Route path="/portfolio/:portfolioId/exchange/:exchange/security/:secid" element={<ExchangeSecurityInfo/>} />
                    </Routes>
                </>
                :
                <Routes>
                    <Route path="/signup" element={<Signup/>} />
                    <Route path="/signin" element={<Signin props={accountService}/>} />
                    <Route path="*" element={<NotFound/>} />
                </Routes>
            }

        </div>
    );
}
