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
public class Lmgtfy extends Plugin {

    public static final String TargetUrl = "https://lmgtfy.com/?q=";

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("ShiroUsagi", 497555706073841671L) };
        manifest.description = "Generates a LMGTFY link.";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/ShiroBlank/AliucordPlugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        commands.registerCommand("lmgtfy", "Generates a LMGTFY link.", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");
            if (msg == null) return new CommandsAPI.CommandResult(msg);
            StringBuilder UrlResult = null;
            try {
                UrlResult = new StringBuilder(TargetUrl+msg.trim().replace(" ","+"));
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
