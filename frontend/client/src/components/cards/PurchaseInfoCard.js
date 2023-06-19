import React from "react";

export function PurchaseInfoCard({purchase}) {
    return (
        <tr>
            <td>
                {purchase.price}
            </td>
            <td>
                {purchase.quantity}
            </td>
        </tr>
    )
}