package ru.gigabyte_artur.warcastleduel.neuro_net;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import ru.gigabyte_artur.warcastleduel.neuro_net.Neuron;

public class DenseLayer extends Layer
{
    private ArrayList<Neuron> Neurons = new ArrayList<>();      // Нейроны, входящие в слой.

    // Конструктор копирования.
    public DenseLayer(DenseLayer layer1)
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        for (Neuron curr_neuron:layer1.GetNeurons())
        {
            Neuron new_neuron = new Neuron();       // TODO: копировнаие аксонов.
            this.AddNeuron(new_neuron);
        }
    }

    public DenseLayer()
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public DenseLayer(Layer layer1)
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    // Добавляет в текущий слой нейрон neuron_in.
    public void AddNeuron(Neuron neuron_in)
    {
        Neurons.add(neuron_in);
        neuron_in.SetParentLayer(this);
    }

    // Добавляет в текущий слой count_in нейронов.
    public void GenerateLayer(int count_in, boolean is_input_in, boolean is_output_in)
    {
        for (int i = 0; i < count_in; i++)
        {
            Neuron new_neuron = new Neuron();
            this.AddNeuron(new_neuron);
        }
        this.is_input = is_input_in;
        this.is_output = is_output_in;
    }

    // Выводит значения нейронов текущего слоя.
    public void ShowLayer()
    {
        double curr_signal;
        for (Neuron curr_neuron: Neurons)
        {
            curr_signal = curr_neuron.GetSignal();
            System.out.print(curr_signal + " ");
        }
        System.out.println();
    }

    // Возвращает количество нейронов в слое.
    public int GetSize()
    {
        return this.Neurons.size();
    }

    // Возвращает все нейроны слоя.
    public ArrayList<Neuron> GetNeurons()
    {
        return Neurons;
    }

    // Обнуляет сигналы во всех нейронах слоя.
    public void EmptySignals()
    {
        for (Neuron curr_neuron:Neurons)
        {
            curr_neuron.SetSignal(0);
        }
    }

    // Устанавливает нейрону с номером neuron_id_in сигнал signal_in.
    public void SetSignalToNeuron(int neuron_id_in, double signal_in)
    {
        Neuron neuron_chng;
        if (this.Neurons.size() >= neuron_id_in)
        {
            neuron_chng = this.Neurons.get(neuron_id_in);
            neuron_chng.SetSignal(signal_in);
        }
        else
        {
            System.out.println("Недостаточно нейронов для установки сигнала");
        }
    }

    // Возвращает максимальный сигнал в слое.
    private double MaxSignal()
    {
        ArrayList<Neuron> TargetNeurons;
        double rez = 0, curr_signal;
        TargetNeurons = this.GetNeurons();
        for (Neuron curr_target_neurons : TargetNeurons)
        {
            curr_signal = curr_target_neurons.GetSignal();
            if (Math.abs(curr_signal) > rez)
                rez = Math.abs(curr_signal);
        }
        return rez;
    }

    // Компилирует текущий слой.
    @Override
    public void CompileLayer(Layer next_layer_in)
    {
        ArrayList <Neuron> target_neurons;
        ArrayList <Neuron> source_neurons = new ArrayList<>();
        if (next_layer_in instanceof DenseLayer)
        {
            target_neurons = ((DenseLayer)next_layer_in).GetNeurons();
            source_neurons = this.GetNeurons();
            for (Neuron curr_target_neurons: target_neurons)
            {
                for (Neuron curr_source_neurons:source_neurons)
                    curr_target_neurons.GenerateAddAxon(curr_source_neurons, 0);
            }
        }
        else
        {
            // Следующий слой не полносвязный. Не обновляем.
        }
    }

    // Номализует текущий слой.
    public void Normalize()
    {
        ArrayList<Neuron> TargetNeurons;
        double curr_signal, max_signal, new_signal;
        TargetNeurons = this.GetNeurons();
        max_signal = this.MaxSignal();
        if (max_signal != 0)
        {
            for (Neuron curr_target_neurons : TargetNeurons)
            {
                curr_signal = curr_target_neurons.GetSignal();
                new_signal = curr_signal / max_signal;
                curr_target_neurons.SetSignal(new_signal);
            }
        }
        else
        {
            // Нулевой максимум. Пропускаем.
        }
    }

    // С вероятностью probablity_in устанавливает всем аксонам текущего слоя
    // случайные веса.
    public void Mutate(double probablity_in)
    {
        for (Neuron curr_neuron:this.Neurons)
        {
            curr_neuron.Mutate(probablity_in);
        }
    }

    // Возвращает нейрон с идентификатором id_in, принадлежащий текущему слою.
    public Neuron FindNeuronById(String id_in)
    {
        Neuron rez = new Neuron();
        String curr_id_neuron;
        ArrayList<Neuron> Neurons = this.GetNeurons();
        for (Neuron curr_neuron:Neurons)
        {
            curr_id_neuron = curr_neuron.GetId();
            if (curr_id_neuron.equals(id_in))
            {
                rez = curr_neuron;
            }
        }
        return rez;
    }

    // Вычисляет сигналы нейронов на текущем слое.
    @Override
    public void CalcSignalsLayer()
    {
        ArrayList<Neuron> TargetNeurons;
        ArrayList<Axon> SourceAxons = new ArrayList<>();
        double sum_signal, curr_weight, curr_signal;
        Neuron source_neuron;
        // Вычисление сигналов.
        TargetNeurons = this.GetNeurons();
        for (Neuron curr_target_neurons : TargetNeurons)
        {
            SourceAxons = curr_target_neurons.GetAxons();
            sum_signal = 0;
            for (Axon curr_source_axons : SourceAxons)
            {
                curr_weight = curr_source_axons.GetWeight();
                source_neuron = curr_source_axons.GetSource();
                curr_signal = source_neuron.GetSignal();
                sum_signal = sum_signal + (curr_signal * curr_weight);
            }
            curr_target_neurons.SetSignal(sum_signal);
        }
        // Нормализация.
        if (!this.GetIsOutput())
        {
            this.Normalize();
        }
    }

    // Выставляет случаные веса на текущем слое.
    @Override
    public void RandomWeightsLayer()
    {
        double new_weight;
        final Random random = new Random();
        for (Neuron curr_neuron: this.GetNeurons())
        {
            for (Axon curr_axon: curr_neuron.GetAxons())
            {
                new_weight = (random.nextDouble()*2) - 1;
                curr_axon.SetWeight(new_weight);
            }
        }
    }
}
