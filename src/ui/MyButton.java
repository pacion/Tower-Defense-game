package ui;

import java.awt.*;

public class MyButton {
    public int x, y, width, height;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;
    private int id;

    // normal button
    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;

        initBounds();
    }

    // tile button
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics graphics) {
        drawBody(graphics);

        drawBorder(graphics);

        drawText(graphics);
    }

    private void drawBody(Graphics graphics) {
        if(mouseOver) {
            graphics.setColor(Color.GRAY);
        } else {
            graphics.setColor(Color.WHITE);
        }

        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, width, height);
    }

    private void drawBorder(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, width, height);

        if(mousePressed) {
            graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
            graphics.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    private void drawText(Graphics graphics) {
        int w = graphics.getFontMetrics().stringWidth(text);
        int h = graphics.getFontMetrics().getHeight();

        graphics.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 3);
    }

    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }
}
