package ru.gigabyte_artur.warcastleduel.neuro_net;

import java.util.ArrayList;
import java.util.HashMap;

public class NeuroNet
{
    private ArrayList<Layer> Layers = new ArrayList<>();

    // Конструктор.
    public NeuroNet()
    {

    }

    public NeuroNet copy()
    {
        return new NeuroNet(this);
    }

    // Конструктор копирования.
    public NeuroNet (NeuroNet net1)
    {
        this.Layers.clear();
        for (Layer curr_layer:net1.Layers)
        {
            Layer new_layer = new Layer(curr_layer);
            this.Layers.add(new_layer);
        }
        this.Compile();
    }

    // Добавляет в модель слой layer_in.
    public void AddLayer(Layer layer_in)
    {
        this.Layers.add(layer_in);
        layer_in.SetParentNeuroNet(this);
    }

    // Генерирует слой с размером count_in и добавляет его в модель.
    public void GenerateAddDenseLayer(int count_in, boolean is_input_in, boolean is_output_in)
    {
        DenseLayer new_layer = new DenseLayer();
        new_layer.GenerateLayer(count_in, is_input_in, is_output_in);
        this.AddLayer(new_layer);
    }

    // Выводит модель на экран.
    public void Show()
    {
        for (Layer curr_layer:this.Layers)
        {
            curr_layer.ShowLayer();
        }
    }

    // Получает следующий за layer_in слой в модели.
    private Layer NextLayer(Layer layer_in)
    {
        Layer rez = new Layer();
        boolean found = false;
        for (Layer curr_layer:this.Layers)
        {
            if (found)
            {
                rez = curr_layer;
                break;
            }
            if (curr_layer == layer_in)
            {
                found = true;
            }
        }
        return rez;
    }

    // Компилирует модель.
    public void Compile()
    {
        Layer next_layer;
        for (Layer curr_layer:this.Layers)
        {
            next_layer = this.NextLayer(curr_layer);
            if (next_layer != null)
            {
                curr_layer.CompileLayer(next_layer);
            }
        }
    }

    // Копирует модель из нейросети neuro_net_in.
    public void CopyModel(NeuroNet neuro_net_in)
    {
//        NeuroNet rez = new NeuroNet();
//        rez = neuro_net_in;
//        return rez;
        // TODO: Написать контруктор копирования.
//        this = neuro_net_in;
    }

    // Устанавливает во входном нейроне neuron_id_in значение сигнала signal_in.
    public void SetInputSignal(int neuron_id_in, double signal_in)
    {
        Layer input_layer;
        if (this.Layers.size() != 0)
        {
            input_layer = this.Layers.get(0);
            if (input_layer instanceof DenseLayer)
            {
                ((DenseLayer)input_layer).SetSignalToNeuron(neuron_id_in, signal_in);
            }
            else
            {
                // Не полносвязный слой. Ничего не делаем.
            }
        }
        else
        {
            System.out.println("В нейронной сети недосточно слоёв для установки начального сигнала");
        }
    }

    // Вычисляет сигналы в текущей нейронной сети по слоям.
    public void CalcSignals()
    {
        for (Layer curr_layer: Layers)
        {
            if (!curr_layer.GetIsInput())
            {
                curr_layer.CalcSignalsLayer();
            }
            else
            {
                // Попускаем входной слой.
            }
        }
    }

    // Возваращает сигналы выходного слоя.
    public HashMap<Integer,Double> GetArrayOutputSignals()
    {
        HashMap<Integer,Double> rez = new HashMap<Integer,Double>();
        ArrayList<Neuron> last_neurons = new ArrayList<>();
        Layer last_layer;
        int layers_size, counter;
        double curr_signal;
        layers_size = this.Layers.size();
        if (layers_size > 0)
        {
            last_layer = this.Layers.get(layers_size - 1);
            if (last_layer instanceof DenseLayer)
            {
                last_neurons = ((DenseLayer) last_layer).GetNeurons();
                counter = 0;
                for (Neuron current_neuron : last_neurons)
                {
                    curr_signal = current_neuron.GetSignal();
                    rez.put(counter, curr_signal);
                    counter++;
                }
            }
            else
            {
                System.out.println("Неизвестный вариант слоя");
                rez = new HashMap<Integer, Double>();
            }
        }
        else
        {
            System.out.println("В нейронной сети нет слоёв");
            rez = new HashMap<Integer, Double>();
        }
        return rez;
    }

    // Возвращает сигнал последнего нейрона.
    public double GetOutputSignal()
    {
        double rez;
        rez = 0;
        HashMap<Integer,Double> signals = this.GetArrayOutputSignals();
        if (!signals.isEmpty())
        {
            rez = signals.get(0);
        }
        else
        {
            rez = 0;
        }
        return rez;
    }

    // Устанавливает во все веса случайные значения.
    public void RandomWeights()
    {
        for (Layer curr_layer:this.Layers)
        {
            curr_layer.RandomWeightsLayer();
        }
    }

    // С вероятностью probablity_in устанавливает всем аксонам текущей нейронной сети
    // случайные веса.
    public void Mutate(double probablity_in)
    {
        for (Layer curr_layer:this.Layers)
        {
            curr_layer.Mutate(probablity_in);
        }
    }

    // Возвращает слои текущей сети.
    public ArrayList<Layer> GetLayers()
    {
        return Layers;
    }

    // Выбирает выходные сигналы из OutputSignals_in среди массива разрешённых Allowed_in.
    public static int SelectNeuronFromAllowed(HashMap<Integer,Double> OutputSignals_in, ArrayList<Integer> Allowed_in)
    {
        int rez = -1, MaxNeuron = -1, CurrentNeuron;
        double MaxSignal = -999999, CurrentSignal;
        for (HashMap.Entry<Integer,Double> entry: OutputSignals_in.entrySet())
        {
            CurrentNeuron = entry.getKey();
            if (Allowed_in.indexOf(CurrentNeuron) != -1)
            {
                CurrentSignal = entry.getValue();
                if (CurrentSignal > MaxSignal)
                {
                    MaxSignal = CurrentSignal;
                    rez = CurrentNeuron;
                }
            }
            else
            {
                // Нет в списке разрешенных. Пропускаем.
            }
        }

        return rez;
    }

    // Вычисляет сигналы текущей нейросети и возвращает выходные сигналы среди массива разрешённых AllowedSignals_in.
    public int CalcSignalsAndGetAllowedOutput(ArrayList<Integer> AllowedSignals_in)
    {
        int rez = -1;
        this.CalcSignals();
        HashMap<Integer,Double> OutputSignals = this.GetArrayOutputSignals();
        rez = NeuroNet.SelectNeuronFromAllowed(OutputSignals, AllowedSignals_in);
        return rez;
    }
}
