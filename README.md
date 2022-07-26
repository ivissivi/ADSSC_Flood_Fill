# ADSSC_Flood_Fill
## Preview
### Flood Fill algorithm demo using BFS

BFS (starting-position, replacement-color):
1. Create an empty queue.
2. Enqueue starting pixel and mark it as processed.
3. Loop until the queue is empty.

  Dequeue the front node and process it.
  Replace the color of the current pixel (popped node) with that of the replacement.
  Process all eight adjacent pixels of the current pixel and enqueue each valid pixel that has the same color as that of the current pixel.

<p align="center">
  <img src="preview/picture1.png?raw=true" />
</p>

Set the current position (x, y).

<p align="center">
  <img src="preview/picture2.png?raw=true" />
  <img src="preview/picture3.png?raw=true" />
</p>

Start position and all the connected nodes are being colored pink.

<p align="center">
  <img src="preview/picture4.png?raw=true" />
</p>
