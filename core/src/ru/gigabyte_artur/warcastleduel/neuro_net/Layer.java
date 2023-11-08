package ru.gigabyte_artur.warcastleduel.neuro_net;

import java.util.UUID;

public class Layer
{
//    private ArrayList<Neuron> Neurons = new ArrayList<>();      // Нейроны, входящие в слой.
    private NeuroNet parent_neuro_net;                          // Родительская нейросеть.
    boolean is_input;                                           // Признак входного слоя.
    boolean is_output;                                          // Признак выходного слоя.
    String id;                                                  // Идентификатор.

    // Мутирует текущий слой с вероятностью probablity_in.
    public void Mutate(double probablity_in)
    {

    }

    // Конструктор.
    public Layer()
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    // Конструктор копирования.
    public Layer(Layer layer1)
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    // Выводит значения нейронов текущего слоя.
    public void ShowLayer()
    {
        System.out.println("Layer " + this.GetId());
    }

    // Возвращает родительскую нейронную сеть.
    public NeuroNet GetParentNeuroNet()
    {
       return this.parent_neuro_net;
    }

    // Устанавливает родительскую нейронную сеть в parent_neuro_net_in.
    public void SetParentNeuroNet(NeuroNet parent_neuro_net_in)
    {
        this.parent_neuro_net = parent_neuro_net_in;
    }

    // Возвращает идентификатор слоя.
    public String GetId()
    {
        return this.id;
    }

    // Устанавливает идентификатор в id_in.
    public void SetId(String id_in)
    {
       this.id = id_in;
    }

    // Возвращает, является ли этот слой входным.
    public boolean GetIsInput()
    {
        return this.is_input;
    }

    // Возвращает, является ли этот слой выходным.
    public boolean GetIsOutput()
    {
        return this.is_output;
    }

    public void SetOptions(String id_in, Boolean is_input_in, Boolean is_output_in)
    {
        this.id = id_in;
        this.is_input = is_input_in;
        this.is_output = is_output_in;
    }

    // Компилирует текущий слой. Будет переопределён в потомках.
    public void CompileLayer(Layer next_layer_in)
    {

    }

    // Вычисляет сигналы в слое. Будет переопределён в потомках.
    public void CalcSignalsLayer()
    {

    }

    // Выставляет в текщем слое случайные веса. Будет переопределён в потомках.
    public void RandomWeightsLayer()
    {

    }
}
