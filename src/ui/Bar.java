package ui;

import java.awt.*;

public class Bar {
    protected int x, y, width, height;

    public Bar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawButtonFeedback(Graphics graphics, MyButton button) {
        if (button.isMouseOver())
            graphics.setColor(Color.white);
        else
            graphics.setColor(Color.black);

        graphics.drawRect(button.x, button.y, button.width, button.height);

        if (button.isMousePressed()) {
            graphics.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
            graphics.drawRect(button.x + 2, button.y + 2, button.width - 4, button.height - 4);
        }
    }
}
