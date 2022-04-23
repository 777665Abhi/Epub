package com.sachy.epubreader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class EPubDemo extends AppCompatActivity {
    WebView webview;
    private Book book;
    private String line, line1="", finalstr="";
    int i = 0;
    private String fullBook;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub_demo);
        webview=findViewById(R.id.webview);

        triggerFile();
    }

    public void triggerFile()
    {
        AssetManager assetManager = getAssets();
        try {
            // find InputStream for book
            InputStream epubInputStream = assetManager
                    .open("Alice.epub");

            // Load Book from inputStream
            book = (new EpubReader()).readEpub(epubInputStream);

            // Log the book's authors
            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

            // Log the book's title
            Log.i("epublib", "title: " + book.getTitle());

            // Log the book's coverimage property
            Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
                    .getInputStream());
            Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
                    + coverImage.getHeight() + " pixels");

            //imageView.setImageBitmap(coverImage);
            // Log the tale of contents
            //logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
            getEntireBook();
        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }
    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth){
        // Load entire text into particular no. of lines and display each
        // then with next button navigate through pages

        String lineXX = "p";
        if(tocReferences == null){
            System.out.println("---->>>"+tocReferences);
            return ;
        }

        for(TOCReference tocReference:tocReferences){

            StringBuilder tocString = new StringBuilder();

            for(int i=0;i<depth;i++){
                tocString.append("\t");
            }

            tocString.append(tocReference.getTitle());
            //tv.setText(tocString.toString());
            //Log.i("epulib", tocString.toString());

            try {
                InputStream is = tocReference.getResource().getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));

                while ((line = r.readLine()) != null) {

                    //String lineX =  Html.fromHtml(line).toString();
                    //lineXX = lineX;
                    Log.v("line" + i, Html.fromHtml(line).toString());

                    line1 = line1.concat(Html.fromHtml(line).toString());

                }
                finalstr = finalstr.concat("\n").concat(line1);
                //tv.setText(finalstr);
                //Log.i("Content " + i, finalstr);
                i++;
            } catch (IOException e) {

            }


            logTableOfContents(tocReference.getChildren(), depth+1);
        }
        //return lineXX;
        webview.loadDataWithBaseURL("", finalstr, "text/html", "UTF-8", "");
    }
    public void getEntireBook(){
        String line, linez = null;
        Spine spine = book.getSpine();
        Resource res;
        List<SpineReference> spineList = spine.getSpineReferences() ;

        int count = spineList.size();
        int start = 0;

        StringBuilder string = new StringBuilder();
        for (int i = start; count > i; i = i +1) {
            res = spine.getResource(i);

            try {
                InputStream is = res.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                try {
                    while ((line = reader.readLine()) != null) {
                        linez =   string.append(line + "\n").toString();
                    }

                } catch (IOException e) {e.printStackTrace();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadData(linez, "text/html", "utf-8");

    }
}

/**Part 2*/
//}
//    private WebView webview;
//    private String line, line1="", finalstr="";
//    int i = 0;
//    private String fullBook;
//    private Book book;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_epub_demo);
//        webview = (WebView) findViewById(R.id.webview);
//        AssetManager assetManager = getAssets();
//
//        try{
//            //find input Stream for book
//            InputStream epubInputStream = assetManager.open("book.epub");
//
//            //Load book from input stream
//            book = (new EpubReader()).readEpub(epubInputStream);
//
//            Log.i("epublib", "title: "+book.getTitle());
//
//            //Log the book's cover image property
//            //Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());
//
//            //Log.i("epublib", "CoverImage is" + coverImage.getWidth()+" by "+coverImage.getHeight()+" pixels");
//
//            //Log the tables of contents
//            logTableOfContents(book.getTableOfContents().getTocReferences(),0);
//
//            fullBook = getEntireBook();
//
//
//        }
//        catch(IOException e){
//            Log.e("epublib", e.getMessage());
//        }
//        String javascrips = "";
//        try {
//            // InputStream input = getResources().openRawResource(R.raw.lights);
//            InputStream input = this.getAssets().open(
//                    "book.epub");
//
//            int size;
//            size = input.available();
//            byte[] buffer = new byte[size];
//            input.read(buffer);
//            input.close();
//            // byte buffer into a string
//            javascrips = new String(buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // String html = readFile(is);
//
//        webview.loadDataWithBaseURL("file:///android_asset/", javascrips,
//                "application/epub+zip", "UTF-8", null);
//        //webview.setwe
//    }
//
//    private void logTableOfContents(List<TOCReference> tocReferences, int depth){
//
//        if(tocReferences == null){
//            System.out.println("---->>>"+tocReferences);
//            return;
//        }
//
//        for(TOCReference tocReference:tocReferences){
//
//            StringBuilder tocString = new StringBuilder();
//
//            for(int i=0;i<depth;i++){
//                tocString.append("\t");
//            }
//
//            tocString.append(tocReference.getTitle());
//            Log.i("epulib", tocString.toString());
//
//            try {
//                InputStream is = tocReference.getResource().getInputStream();
//                BufferedReader r = new BufferedReader(new InputStreamReader(is));
//
//                while ((line = r.readLine()) != null) {
//                    // line1 = Html.fromHtml(line).toString();
//                    Log.v("line" + i, Html.fromHtml(line).toString());
//                    // line1 = (tocString.append(Html.fromHtml(line).toString()+
//                    // "\n")).toString();
//                    line1 = line1.concat(Html.fromHtml(line).toString());
//                }
//                finalstr = finalstr.concat("\n").concat(line1);
//                Log.i("Content " + i, finalstr);
//                i++;
//            } catch (IOException e) {
//
//            }
//
//
//            logTableOfContents(tocReference.getChildren(), depth+1);
//        }
//        webview.loadDataWithBaseURL("", finalstr, "text/html", "UTF-8", "");
//    }
//
//    public String getEntireBook(){
//        String line, linez = null;
//        Spine spine = book.getSpine();
//        Resource res;
//        List<SpineReference> spineList = spine.getSpineReferences() ;
//
//        int count = spineList.size();
//        int start = 0;
//
//        StringBuilder string = new StringBuilder();
//        for (int i = start; count > i; i = i +1) {
//            res = spine.getResource(i);
//
//            try {
//                InputStream is = res.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                try {
//                    while ((line = reader.readLine()) != null) {
//                        linez =   string.append(line + "\n").toString();
//                    }
//
//                } catch (IOException e) {e.printStackTrace();}
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return linez;
//
//
//    }
//
//}

/**Part 1*/
//    WebView webview;
//    String line, line1 = "", finalstr = "";
//    int i = 0;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_epub_demo);
//        webview = (WebView) findViewById(R.id.webview);
//        AssetManager assetManager = getAssets();
//        try {
//            // find InputStream for book
//            InputStream epubInputStream = assetManager
//                    .open("Alice.epub");
//
//            // Load Book from inputStream
//            Book book = (new EpubReader()).readEpub(epubInputStream);
//
//            // Log the book's authors
//            Log.i("author", " : " + book.getMetadata().getAuthors());
//
//            // Log the book's title
//            Log.i("title", " : " + book.getTitle());
//
//            /* Log the book's coverimage property */
//            // Bitmap coverImage =
//            // BitmapFactory.decodeStream(book.getCoverImage()
//            // .getInputStream());
//            // Log.i("epublib", "Coverimage is " + coverImage.getWidth() +
//            // " by "
//            // + coverImage.getHeight() + " pixels");
//
//            // Log the tale of contents
//            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
//        } catch (IOException e) {
//            Log.e("epublib exception", e.getMessage());
//        }
//
//        String javascrips = "";
//        try {
//            // InputStream input = getResources().openRawResource(R.raw.lights);
//            InputStream input = this.getAssets().open(
//                    "Alice.epub");
//
//            int size;
//            size = input.available();
//            byte[] buffer = new byte[size];
//            input.read(buffer);
//            input.close();
//            // byte buffer into a string
//            javascrips = new String(buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // String html = readFile(is);
//
//        webview.loadDataWithBaseURL("file:///android_asset/", javascrips,
//                "application/epub+zip", "UTF-8", null);
//    }
//
//    @SuppressWarnings("unused")
//    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
//        if (tocReferences == null) {
//            return;
//        }
//
//        for (TOCReference tocReference : tocReferences) {
//            StringBuilder tocString = new StringBuilder();
//            for (int i = 0; i < depth; i++) {
//                tocString.append("\t");
//            }
//            tocString.append(tocReference.getTitle());
//            Log.i("TOC", tocString.toString());
//
//            try {
//                InputStream is = tocReference.getResource().getInputStream();
//                BufferedReader r = new BufferedReader(new InputStreamReader(is));
//
//                while ((line = r.readLine()) != null) {
//                    // line1 = Html.fromHtml(line).toString();
//                    Log.v("line" + i, Html.fromHtml(line).toString());
//                    // line1 = (tocString.append(Html.fromHtml(line).toString()+
//                    // "\n")).toString();
//                    line1 = line1.concat(Html.fromHtml(line).toString());
//                }
//                finalstr = finalstr.concat("\n").concat(line1);
//                // Log.v("Content " + i, finalstr);
//                i++;
//            } catch (IOException e) {
//
//            }
//
//            logTableOfContents(tocReference.getChildren(), depth + 1);
//        }
//        webview.loadDataWithBaseURL("", finalstr, "text/html", "UTF-8", "");
//    }
//}