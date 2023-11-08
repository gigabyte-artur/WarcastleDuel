package ru.gigabyte_artur.warcastleduel.neuro_net;

public class Axon
{
    private Neuron source = new Neuron();
    private double weight;

    // Конструктор.
    public Axon()
    {

    }

    // Конструктор копирования.
    public Axon(Axon Axon1)
    {

    }

    //  Устанавливает екущему нейрону связь с нейроном neuron_in с весом weight_in.
    public void Set(Neuron neuron_in, double weight_in)
    {
        this.source = neuron_in;
        this.weight = weight_in;
    }

    // Получает вход текущей связи.
    public Neuron GetSource()
    {
        return this.source;
    }

    // Получает вес текущей связи.
    public double GetWeight()
    {
        return this.weight;
    }

    // Устанавливает вес связи в weight_in.
    public void SetWeight(double weight_in)
    {
        this.weight = weight_in;
    }
}
