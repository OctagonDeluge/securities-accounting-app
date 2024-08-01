import React from "react";

export function PaymentCard({payment}) {
    return (
        <tr>
            <td>
                {payment.paymentDate}
            </td>
            <td>
                {payment.cost} {payment.currency}
            </td>
        </tr>
    )
}