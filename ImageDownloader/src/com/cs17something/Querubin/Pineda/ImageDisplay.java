package com.cs17something.Querubin.Pineda;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ImageDisplay extends Activity {
	ArrayList<String> arrayImages;

	ImageAdapter adapter;
	ListView list;
	String[] links = new String[] {
			"http://ww1.prweb.com/prfiles/2010/05/11/1751474/gI_TodoforiPadAppIcon512.png.jpg",
			"http://cdn4.iosnoops.com/wp-content/uploads/2011/08/Icon-Gmail_large-250x250.png",
			"http://kelpbeds.files.wordpress.com/2012/02/lens17430451_1294953222linkedin-icon.jpg?w=450",
			"http://snapknot.com/blog/wp-content/uploads/2010/03/facebook-icon-copy.jpg",
			"https://lh3.googleusercontent.com/-ycDGy_fZVZc/AAAAAAAAAAI/AAAAAAAAAAc/Q0MmjxPCOzk/s250-c-k/photo.jpg",

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images);

		list = (ListView) findViewById(R.id.list);
		// final Button b = (Button)findViewById(R.id.requestButton);

		arrayImages = new ArrayList<String>();/*
											 * b.setOnClickListener(new
											 * OnClickListener() {
											 * 
											 * @Override public void
											 * onClick(View v) {
											 * if(addLinks()==false)
											 * b.setEnabled(false);
											 * 
											 * } });
											 */
		addLinks();
	}

	public boolean addLinks() {
		boolean ret = true;
		/*
		 * if(counter<5){
		 * 
		 * while(counter<links.length) {
		 * 
		 * arrayImages.add(links[counter]);
		 * 
		 * counter++; } adapter = new ImageAdapter(this,arrayImages);
		 * 
		 * 
		 * 
		 * list.setAdapter(adapter); } else ret=false;
		 */
		int counter = 0;
		while (counter < links.length) {
			arrayImages.add(links[counter]);
			counter++;
		}
		adapter = new ImageAdapter(this, arrayImages);

		list.setAdapter(adapter);
		return ret;
	}

	class ImageAdapter extends BaseAdapter {
		ArrayList<String> data;
		private LayoutInflater inflater = null;
		Context context;

		public ImageAdapter(Context context, ArrayList<String> data) {
			this.data = data;
			this.context = context;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final String link = data.get(position);

			final View vi = (convertView == null) ? inflater.inflate(
					R.layout.list_row, null) : convertView;

			((TextView) vi.findViewById(R.id.imageLink)).setText("Link: "
					+ link);

			new AsyncTask<Void, Void, String>() {
				ImageView thumb_image;
				Bitmap temp;

				protected void onPreExecute() {
					thumb_image = (ImageView) vi.findViewById(R.id.image);
					thumb_image.setTag(link);
				}

				@Override
				protected String doInBackground(Void... params) {
					try {
						temp = getLogo(link);
					} catch (Exception e) {

					}

					return link;
				}

				@Override
				protected void onPostExecute(String result) {
					if (thumb_image.getTag().toString().equals(link)) {
						thumb_image.setImageBitmap(temp);
						vi.findViewById(R.id.loaderImage).setVisibility(
								View.GONE);
						thumb_image.setVisibility(View.VISIBLE);
					} else {

						vi.findViewById(R.id.loaderImage).setVisibility(
								View.VISIBLE);

						thumb_image.setVisibility(View.GONE);
					}
					super.onPostExecute(result);
				}
			}.execute();

			return vi;
		}

	}

	public static Bitmap getLogo(String str) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(str)
					.getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
}
