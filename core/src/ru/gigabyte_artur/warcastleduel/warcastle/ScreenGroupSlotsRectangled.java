package ru.gigabyte_artur.warcastleduel.warcastle;

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
            if (CurrentElement.equals(Element_in))
            {
                Curr_Slot.EmptyElement();
                break;
            }
            else
            {
                // Выполняем поиск далее.
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

    /** Прикрепляет элемент Element_in к первому свободному слоту.*/
    public boolean LinkElementToFirstEmptySlot(ScreenRectangledElement Element_in)
    {
        boolean rez = false;
        ScreenSlotRectangled EmptySlot = new ScreenSlotRectangled();
        if (isThereEmptySlot())
        {
            EmptySlot = GetFirstEmptySlot();
            EmptySlot.setLinkedElement(Element_in);
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
}
