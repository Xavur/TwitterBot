//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package asynctask;

import javafx.application.Platform;
import twitter4j.TwitterException;

public abstract class AsyncTask<T1, T2, T3> {
    private boolean daemon = true;
    private T1[] params;
    public final Thread backGroundThread = new Thread(new Runnable() {
        public void run() {
            T3 param = AsyncTask.this.doInBackground(AsyncTask.this.params);
            Platform.runLater(() -> {
                AsyncTask.this.onPostExecute(param);
            });
        }
    });

    public AsyncTask() {
    }

    public abstract void onPreExecute();

    public abstract T3 doInBackground(T1... var1);

    public abstract void onPostExecute(T3 var1);

    public abstract void progressCallback(T2... var1);

    public void publishProgress(T2... progressParams) {
        Platform.runLater(() -> {
            this.progressCallback(progressParams);
        });
    }

    public void execute(T1... params) {
        this.params = params;
        Platform.runLater(() -> {
            this.onPreExecute();
            this.backGroundThread.setDaemon(this.daemon);
            this.backGroundThread.start();
        });
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public final boolean isInterrupted() {
        return this.backGroundThread.isInterrupted();
    }

    public final boolean isAlive() {
        return this.backGroundThread.isAlive();
    }
}
