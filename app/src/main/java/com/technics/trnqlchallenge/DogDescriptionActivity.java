package com.technics.trnqlchallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DogDescriptionActivity extends AppCompatActivity {

    private ParseUser user;
    private static final int SELECT_PHOTO = 100;

    private static final String[] BREEDS = new String[] {
            "Affenpinscher","Affenpoo","Afghan Hound","Afghan Retriever","Airedale Terrier","Akita","Alaskan Klee Kai","Alaskan Malamute","Alaskan Noble Companion Dog","American Bulldog","American English Coonhound","American Eskimo Dog","American Foxhound","American Labrador Retriever","American Mastiff","American Pit Bull Terrier","American Staffordshire Terrier","American Water Spaniel","Anatolian Shepherd","Aussiedoodle","Australian Cattle Dog","Australian Kelpie","Australian Shepherd","Australian Terrier","Azawakh","Bagle Hound","Basenji","Bassador","Basset Hound","Bassetoodle","Beagle","Bearded Collie","Beauceron","Bedlington Terrier","Belgian Malinois","Belgian Sheepdog","Belgian Shepherd Malinois","Belgian Tervuren","Bergamasco","Berger Picard","Bernedoodle","Bernese Mountain Dog","Bichon Frise","Bichon Yorkie","Bichpoo","Black and Tan Coonhound","Black Russian Terrier","Blackmouth Cur","Bloodhound","Bluetick Coonhound","Bo Chi","Boerboel","Bolognese","Boodle","Border Collie","Border Terrier","Borkie","Borzoi","Boston Terrier","Bouvier des Flandres","Boxador","Boxer","Boxerdoodle","Boxerman","Boykin Spaniel","Bracco Italiano","bRat (Boston and Rat Terrier)","Brazilian Mastiff","Briard","Brittany","Brussels Griffon","Bugg","Bull Terrier","Bullboxer","Bulldog","Bullmastiff","Cadoodle","Cairn Terrier","Canaan Dog","Cane Corso","Cardigan Welsh Corgi","Carolina Dog","Catahoula Leopard Dog","Caucasian Ovcharka","Cavachon","Cavalier King Charles Spaniel","Cavanese","Cavapoo","Cavoodle","Cesky Terrier","Chabrador","Chesapeake Bay Retriever","Chihuahua","Chinese Crested","Chinese Shar-Pei","Chion","Chipoo","Chiweenie","Chorkie","Chow Chow","Chow Hound","Chug","Cirneco Etna","Clumber Spaniel","Cockalier","Cockapoo","Cocker Spaniel","Cocker Westie","Collie","Corgiador","Coton de Tuléar","Curly-Coated Retriever","Czechoslovakian Wolfdog","Dachshund","Dalmatian","Dandie Dinmont Terrier","Doberman Pinscher","Dogo Argentino","Dogue de Bordeaux","Dorkie","Doxiepoo","Doxle","Dutch Shepherd","English Bull Boxer","English Cocker Spaniel","English Foxhound","English Labrador Retriever","English Mastiff","English Setter","English Shepherd","English Springer Spaniel","English Toy Spaniel","Entlebucher Mountain Dog","Eskipoo","Eurasier","Faux Frenchbo Bulldog","Field Spaniel","Finnish Lapphund","Finnish Spitz","Flat-Coated Retriever","Formosan Mountain Dog","French Bulldog","French Chihuahua","Galgo Español","German Pinscher","German Shepherd","German Shorthaired Pointer","German Spitz","German Wirehaired Pointer","Giant Schnauzer","Glen of Imaal Terrier","Goldador","Golden Retriever","Goldendoodle","Gordon Setter","Grand Basset Griffon Vendéen","Great Dane","Great Pyrenees","Greater Swiss Mountain Dog","Greybull","Greyhound","Griffon Korthal","Harrier","Havachon","Havanese","Havatese","Hokkaido Ken","Ibizan Hound","Icelandic Sheepdog","Insane Chicken Dog","Irish Red and White Setter","Irish Setter","Irish Terrier","Irish Water Spaniel","Irish Wolfhound","Italian Greyhound","Jack Chi","Jack Russell Terrier","Jack-A-Bee","Jackapoo","JackWeenie","Japanese Akita","Japanese Chin","Japanese Spitz","Jug","Kai Ken","Keeshond","Kerry Blue Terrier","Kishu Ken","Komondor","Korean Jindo","Kuvasz","La-Chon","Labradoodle","Labrador Retriever","Lacy","Lagotto Romagnolo","Lakeland Terrier","Leonberger","Lhasa Apso","Lhasapoo","Lowchen","Maltese","Maltichon","Maltipoo","Maltzu","Manchester Terrier","Maremma Sheepdog","Mastador","Mastiff","Mini Bernedoodle","Miniature American Shepherd","Miniature Australian Shepherd","Miniature Bull Terrier","Miniature Dachshund","Miniature Pinscher","Miniature Poodle","Miniature Schnauzer","Morkie","Muggin","Neapolitan Mastiff","New Zealand Huntaway","Newfoundland","Norfolk Terrier","Northern Inuit","Norwegian Buhund","Norwegian Elkhound","Norwegian Lundehund","Norwich Terrier","Nova Scotia Duck Tolling Retriever","Old English Sheepdog","Olde English Bulldogge","Ori Pei","Otterhound","Papichon","Papillon","Parson Russell Terrier","Patterdale Terrier","Peek-a-Pom","Peekapoo","Pekingese","Pembroke Welsh Corgi","Petit Basset Griffon Vendeen","Pharaoh Hound","Pit Bull","Plott","Pointer","Polish Lowland Sheepdog","Pom Chi","Pomapoo","Pomeranian","Pomino","Poodle","Poogle","Portuguese Podengo Pequeno","Portuguese Water Dog","Prague Ratter","Presa Canario","Pug","Pugapoo","Puggle","Pugshire","Puli","Pumi","Pyrenean Shepherd","Rat Terrier","Ratonero Bodeguero Andaluz","Redbone Coonhound","Rhodesian Ridgeback","Rottweiler","Russell Terrier","Saint Berdoodle","Saluki","Samoyed","Samusky","Schipperke","Schipperpoo","Schnoodle","Schweenie","Scotchie","Scottish Deerhound","Scottish Terrier","Sealyham Terrier","Shar-Poo","Sheltiedoodle","Shetland Sheepdog","Shiba Inu","Shichon","Shih Tzu","Shih-poo","Shikoku Ken","Shiloh Shepherd","Shinese","Shiranian","Shorkie Tzu","Siberian Husky","Siberian Retriever","Silken Windhound","Silkshire Terrier","Silky Terrier","Skye Terrier","Sloughi","Smooth Collie","Smooth Fox Terrier","Snorkie","Soft Coated Wheaten Terrier","Spanish Water Dog","Spinone Italiano","St. Bernard","Staffordshire Bull Boxer","Staffordshire Bull Terrier","Standard Poodle","Standard Schnauzer","Sussex Spaniel","Swedish Vallhund","Tamaskan","Tibalier","Tibetan Mastiff","Tibetan Spaniel","Tibetan Terrier","Toy Fox Terrier","Toy Poodle","Treeing Walker Coonhound","Valley Bulldog","Vizsla","Weimaraner","Welsh Springer Spaniel","Welsh Terrier","West Highland White Terrier","Westiepoo","Westion","Whippet","White Shepherd","Whoodle","Wire Fox Terrier","Wirehaired Pointing Griffon","Wirehaired Vizsla","Xoloitzcuintli","Yo-Chon","Yorkie Pom","Yorkiepoo","Yorkshire Terrier"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_description);

        user = ParseUser.getCurrentUser();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, BREEDS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.dogBreed);
        textView.setAdapter(adapter);

        if (getIntent().getBooleanExtra("prePopulate", false)){

            EditText myTextBox = (EditText) findViewById(R.id.dogName);
            AutoCompleteTextView myAutoComplete = (AutoCompleteTextView) findViewById(R.id.dogBreed);

            String dogName = user.getString("dogName");
            myTextBox.setText(dogName);

            String dogBreed = user.getString("dogBreed");
            myAutoComplete.setText(dogBreed);
        }


    }

    public void saveDescription(View View){
        EditText dogName = (EditText)findViewById(R.id.dogName);
        AutoCompleteTextView dogBreed = (AutoCompleteTextView)findViewById(R.id.dogBreed);
        user.put("dogName", dogName.getText().toString());
        user.put("dogBreed", dogBreed.getText().toString());
        user.saveInBackground();

        Intent intent = new Intent(DogDescriptionActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void saveDefaultDescription(View View) {
        user.put("dogName", "Anonymous");
        user.put("dogBreed", "Unknown");
        user.saveInBackground();

        Intent intent = new Intent(DogDescriptionActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data == null) {
                System.out.print("Error occurred");
                return;
            }
            try {
                Context context = this;
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());

                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                user.put("photo", encodedImage);
                user.saveInBackground();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

}
