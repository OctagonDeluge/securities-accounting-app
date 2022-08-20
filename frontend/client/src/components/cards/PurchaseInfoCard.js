import React from "react";

export function PurchaseInfoCard({purchase}) {
    return (
        <tr>
            <td>
                {new Date(purchase.purchaseDate).toLocaleString()}
            </td>
            <td>
                {purchase.price}
            </td>
            <td>
                {purchase.quantity}
            </td>
        </tr>
    )
}