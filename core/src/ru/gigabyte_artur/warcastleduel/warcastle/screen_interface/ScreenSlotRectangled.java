package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

public class ScreenSlotRectangled extends ScreenRectangledElement
{
    private ScreenRectangledElement LinkedElement;      // Привязанный элемент.

    public ScreenSlotRectangled()
    {
        super();
        EmptyElement();
    }

    public ScreenRectangledElement getLinkedElement()
    {
        return LinkedElement;
    }

    public void setLinkedElement(ScreenRectangledElement linkedElement)
    {
        LinkedElement = linkedElement;
    }

    /** Очищает прикреплённый элемент.*/
    public void EmptyElement()
    {
        this.LinkedElement = null;
    }

    /** Проверяет, что элемент пустой.*/
    public boolean isLinkedElementEmpty()
    {
        return LinkedElement == null;
    }

    /** Располагает привязанный элемент к координатам слота.*/
    public void LocateLinkedElementToSlot()
    {
        if (!isLinkedElementEmpty())
        {
            LinkedElement.setPosition(this.getX(), this.getY());
        }
    }

}
