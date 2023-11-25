package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;

public class ScreenSoundList
{
    private HashMap<String, Sound> SoundsMap;       // Ассоциативный массив звуков.

    public ScreenSoundList()
    {
        SoundsMap = new HashMap<>();
    }

    // Добавляет звук с именем name_in, находящимся по пути Path_in.
    public void AddSound(String name_in, String Path_in)
    {
        Sound NewSound = Gdx.audio.newSound(Gdx.files.internal(Path_in));
        this.SoundsMap.put(name_in, NewSound);
    }

    // Воспроизводит звук с именем name_in, если найден.
    public void PlaySound(String name_in)
    {
        if (this.SoundsMap.containsKey(name_in))
        {
            Sound PlayableSound = this.SoundsMap.get(name_in);
            PlayableSound.play();
            PlayableSound.resume();
        }
    }
}
