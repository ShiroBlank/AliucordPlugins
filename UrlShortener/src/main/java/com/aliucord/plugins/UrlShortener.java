package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@SuppressWarnings("unused")
public class UrlShortener extends Plugin {

    public static final String TargetUrl = "https://is.gd/create.php?format=simple&url=";

    private String fetch(String url) throws Throwable {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("User-Agent", "Aliucord");

        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString().trim();
    }


    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("ShiroUsagi", 497555706073841671L) };
        manifest.description = "Shortens the provided url";
        manifest.version = "1.0.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/ShiroBlank/AliucordPlugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        commands.registerCommand("shorturl", "Shortens provided URL", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            StringBuilder UrlResult = null;
            try {
                UrlResult = new StringBuilder(fetch(TargetUrl+msg.trim()));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return new CommandsAPI.CommandResult(UrlResult.toString());
        });

    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }
}
