package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import java.util.ArrayList;

public class ScreenGroupSlotsRectangled
{
    private ArrayList<ScreenSlotRectangled>SlotList = new ArrayList<>();

    /** Создаёт список слотов в количестве num_in*/
    public ScreenGroupSlotsRectangled(int num_in)
    {
        ScreenSlotRectangled NewSlot;
        SlotList.clear();
        for (int c = 0; c < num_in; c++)
        {
            NewSlot = new ScreenSlotRectangled();
            SlotList.add(NewSlot);
        }
    }

    /** Очищает слот, в котором располагается элемент Element_in.*/
    public void EmptySlotByElement(ScreenRectangledElement Element_in)
    {
        ScreenRectangledElement CurrentElement;
        for (ScreenSlotRectangled Curr_Slot:SlotList)
        {
            CurrentElement = Curr_Slot.getLinkedElement();
            if (CurrentElement != null)
            {
                if (CurrentElement.equals(Element_in))
                {
                    Curr_Slot.EmptyElement();
                    break;
                } else
                {
                    // Выполняем поиск далее.
                }
            }
            else
            {
                // Элемент пустой.
            }
        }
    }

    /** Возвращает первый свободный слот.*/
    public  ScreenSlotRectangled GetFirstEmptySlot()
    {
        ScreenSlotRectangled rez = new ScreenSlotRectangled();
        for (ScreenSlotRectangled Curr_Slot:SlotList)
        {
            if (Curr_Slot.isLinkedElementEmpty())
            {
                rez = Curr_Slot;
                break;
            }
            else
            {
                // Выполняем поиск далее.
            }
        }
        return rez;
    }

    /** Определяет наличие свободного слота в списке.*/
    public boolean isThereEmptySlot()
    {
        boolean rez = false;
        for (ScreenSlotRectangled Curr_Slot:SlotList)
        {
            if (Curr_Slot.isLinkedElementEmpty())
            {
                rez = true;
                break;
            }
            else
            {
                // Выполняем поиск далее.
            }
        }
        return rez;
    }

    /** Устанавливает элемент Element_in в слот Slot_chng. */
    public void SetLinkedElementToSlot(ScreenRectangledElement Element_in, ScreenSlotRectangled Slot_chng)
    {
        Slot_chng.setLinkedElement(Element_in);
    }

    /** Прикрепляет элемент Element_in к первому свободному слоту.*/
    public boolean LinkElementToFirstEmptySlot(ScreenRectangledElement Element_in)
    {
        boolean rez = false;
        ScreenSlotRectangled EmptySlot = new ScreenSlotRectangled();
        if (isThereEmptySlot())
        {
            EmptySlot = GetFirstEmptySlot();
            EmptySlot.setLinkedElement(Element_in);
            SetLinkedElementToSlot(Element_in, EmptySlot);
            EmptySlot.LocateLinkedElementToSlot();
        }
        else
        {
            rez = false;
        }
        return rez;
    }

    /** Устанавливает позиции слотов под ряд от точки (x_in; y_in) со сдвигами deltaX_in и deltaY_in по X и Y соответственно.*/
    public void SetListElementsPositions(int x_in, int y_in, int deltaX_in, int deltaY_in)
    {
        int CurrenX = x_in;
        int CurrenY = y_in;
        for (ScreenSlotRectangled Curr_Slot:SlotList)
        {
            Curr_Slot.setPosition(CurrenX, CurrenY);
            CurrenX = CurrenX + deltaX_in;
            CurrenY = CurrenY + deltaY_in;
        }
    }

    /** Восстанавливает позицию подчиённых элементов позии слотов.*/
    public void RestoreLinkedListElementsPositions()
    {
        int LinkedX, LinkedY, SlotX, SlotY;
        ScreenRectangledElement CurrentElement;
        for (ScreenSlotRectangled Curr_Slot:SlotList)
        {
            if (!Curr_Slot.isLinkedElementEmpty())
            {
                CurrentElement = Curr_Slot.getLinkedElement();
                LinkedX = CurrentElement.getX();
                LinkedY = CurrentElement.getY();
                SlotX = Curr_Slot.getX();
                SlotY = Curr_Slot.getY();
                if ((LinkedX != SlotX) | (LinkedY != SlotY))
                {
                    CurrentElement.setPosition(SlotX, SlotY);
                }
                else
                {
                    // Элемент уже находистя на заданной позиции. Не изменяем.
                }
            }
            else
            {
                // Элемент пустой.
            }
        }
    }
}
