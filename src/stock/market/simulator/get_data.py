import csv
import datetime
from binance.client import Client

import websocket
import json
import warnings
warnings.filterwarnings("ignore")

client = Client('API_KEY', 'API_SECRET')
socket = "wss://stream.binance.com:9443/ws/ethusdt@kline_1m"
symbol = 'ETHUSDT'


csvfile = open(
    f'data/{symbol}.csv', 'w', newline='')

csv.writer(csvfile).writerow(
    ['Date', 'Close'])


csvfile.close()


def on_open(ws):
    print('opened connection')


def on_close(ws):
    print('closed connection')


def on_message(ws, message):
    json_message = json.loads(message)
    candle = json_message['k']

    # Open the csv file to add a new entry
    newEntry = [candle['T'], candle['c']]

    with open(f'data/{symbol}.csv', 'a', newline='') as csvfile:
        csv_writer = csv.writer(csvfile)
        csv_writer.writerow(newEntry)
        csvfile.close()


ws = websocket.WebSocketApp(socket, on_open=on_open,
                            on_close=on_close, on_message=on_message)
ws.run_forever()
