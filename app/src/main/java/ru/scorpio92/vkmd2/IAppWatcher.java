package ru.scorpio92.vkmd2;

import android.content.Context;

/**
 * Базовый интерфейс для реализации глобальных точек инициализации и закрытия в приложении
 */
public interface IAppWatcher {
    /**
     * Определяем список действий которые необходимо проделать при запуске приложения
     * до запуска экранов, сервисов и пр.
     * @param context контекст приложения
     */
    void onInitApp(Context context);
    /**
     * Определяем список действий которые необходимо выполнить при закрытии приложения
     * Данный метод обратного вызова сработает либо при ручном вызове
     * либо если система захочет убить приложение (например при нехватке памяти)
     * Стоит именно здесь закрывать и убивать все глобальные инстансы во избежание утечек памяти
     */
    void finishApp();
}
