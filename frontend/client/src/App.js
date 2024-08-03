import './App.css';
import {Navigate, Route, Routes} from "react-router-dom";
import {Portfolio} from "./pages/Portfolio";
import {Security} from "./pages/Security";
import {Exchange} from "./pages/Exchange";
import {ExchangeSecurityInfo} from "./pages/ExchangeSecurityInfo";
import React from "react";
import {links} from "./constants/HeaderLinks";
import {PortfolioSecurityInfo} from "./pages/PortfolioSecurityInfo";
import {Statistics} from "./pages/Statistics";
import {Signup} from "./pages/Signup";
import {Signin} from "./pages/Signin";
import {useWalletService} from "./api/service/WalletService";
import {useAccountService} from "./api/service/AccountService";
import {History} from "./pages/History";
import {Profile} from "./pages/Profile";
import {CustomHeader} from "./components/headers/CustomHeader";

export function App() {
    const walletService = useWalletService();
    const accountService = useAccountService();
    return (
        <div className="App">
            {accountService.auth ?
                    <>
                        <CustomHeader accountService={accountService} walletService={walletService} links={links} />
                        <Routes>
                            <Route path="/portfolio" element={<Portfolio/>} />
                            <Route path="/portfolio/:portfolioId/exchange" element={<Exchange/>} />
                            <Route path="/portfolio/:portfolioId/security" element={<Security/>} />
                            <Route path="/portfolio/:portfolioId/statistics" element={<Statistics/>} />
                            <Route path="/portfolio/:portfolioId/security/:securityId" element={<PortfolioSecurityInfo state={walletService}/>} />
                            <Route path="/portfolio/:portfolioId/exchange/:exchange/security/:secid" element={<ExchangeSecurityInfo state={walletService}/>} />
                            <Route path="/history" element={<History/>} />
                            <Route path="/profile" element={<Profile/>} />
                            <Route path="*" element={<Navigate to={"/portfolio"}/>} />
                        </Routes>
                    </> :
                <Routes>
                    <Route path="/signup" element={<Signup/>} />
                    <Route path="/signin" element={<Signin auth={accountService} wallet={walletService}/>} />
                    <Route path="*" element={<Navigate to={"/signin"} />} />
                </Routes>
            }
        </div>
    );
}
