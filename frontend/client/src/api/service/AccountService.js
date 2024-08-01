import {useState} from "react";

export function useAccountService() {
    const [auth, setAuth] = useState(JSON.parse(localStorage.getItem("auth")) || false);

    return { auth, setAuth };
}