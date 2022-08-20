import React from "react";
import {Cell, Pie, PieChart, Tooltip} from "recharts";

export function SecurityTypeChart({data, namings}) {
    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

    return (
            <PieChart width={300} height={300}>
                <Pie
                    dataKey={namings.dataKey}
                    nameKey={namings.nameKey}
                    isAnimationActive={true}
                    data={data}
                    cx="50%"
                    cy="50%"
                    outerRadius={100}
                    fill="#8884d8"
                    label
                >
                    {data.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                </Pie>
                <Tooltip />
            </PieChart>
    );
}