package a.a.a;

import android.util.Log;

import java.util.ArrayList;

/**
 * A class to keep track of a project's built-in libraries.
 */
public class Kp {

    private final ArrayList<String> libraryNames = new ArrayList<>();
    private final ArrayList<Jp> libraries = new ArrayList<>();

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already.
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public void a(String libraryName) {
        if (!libraryNames.contains(libraryName)) {
            Log.d(Dp.TAG, "Added built-in library \"" + libraryName + "\" to project's dependencies");
            libraryNames.add(libraryName);
            libraries.add(new Jp(libraryName));
            addDependencies(libraryName);
        } else {
            Log.v(Dp.TAG, "Didn't add built-in library \"" + libraryName + "\" to project's dependencies again");
        }
    }

    private void addDependencies(String libraryName) {
        for (String libraryDependency : qq.a(libraryName)) {
            a(libraryDependency);
        }
    }

    /**
     * @return {@link Kp#libraries}
     */
    public ArrayList<Jp> a() {
        return libraries;
    }
}
