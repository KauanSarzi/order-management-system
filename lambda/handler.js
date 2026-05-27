'use strict';

const https = require('https');
const http = require('http');

function fetchOrders(url) {
    return new Promise((resolve, reject) => {
        const client = url.startsWith('https') ? https : http;
        client.get(url, (res) => {
            let data = '';
            res.on('data', (chunk) => { data += chunk; });
            res.on('end', () => {
                try {
                    resolve(JSON.parse(data));
                } catch (e) {
                    reject(new Error('Failed to parse response: ' + e.message));
                }
            });
        }).on('error', reject);
    });
}

exports.handler = async () => {
    const apiUrl = process.env.API_URL;
    if (!apiUrl) {
        throw new Error('Environment variable API_URL is not set');
    }

    const orders = await fetchOrders(`${apiUrl}/orders`);

    const byStatus = { PENDING: 0, PROCESSING: 0, DONE: 0, CANCELLED: 0 };
    let totalAmount = 0;

    for (const order of orders) {
        const s = order.status;
        if (s in byStatus) byStatus[s]++;
        if (order.total != null) totalAmount += Number(order.total);
    }

    const totalOrders = orders.length;
    const averageTicket = totalOrders > 0
        ? (totalAmount / totalOrders).toFixed(2)
        : '0.00';

    return {
        statusCode: 200,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            totalOrders,
            byStatus,
            averageTicket,
            generatedAt: new Date().toISOString(),
        }),
    };
};
