package br.com.testeti.testeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.testeti.testeapp.R;
import br.com.testeti.testeapp.sqlite.TesteObject;

/**
 * Created by rodrigo.cason on 25/05/2016.
 */
public class TesteAdapter extends ArrayAdapter<TesteObject> {

    private Context mContext;

    public TesteAdapter(Context context, int resource, List<TesteObject> items) {
        super(context, resource, items);

        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_teste, null);
        }

        TesteObject p = getItem(position);
        if (p != null) {
            final TextView tvQrCode = (TextView) v.findViewById(R.id.tvQrCode);
            final TextView tvLat = (TextView) v.findViewById(R.id.tvLat);
            final TextView tvLng = (TextView) v.findViewById(R.id.tvLng);
            Button btnAbrirItem = (Button) v.findViewById(R.id.btnAbrirItem);

            btnAbrirItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String conteudo = tvQrCode.getText().toString();

                    if (!conteudo.startsWith("http://") && !conteudo.startsWith("https://"))
                        conteudo = "http://" + conteudo;
                    if (conteudo.contains("play.google.com")) {
                        if (!conteudo.startsWith("market//")) {
                            conteudo = "market://" + conteudo.replace("http://play.google.com/store/apps/", "").replace("https://play.google.com/store/apps/", "");
                        }
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(conteudo));
                    mContext.startActivity(browserIntent);
                }
            });
            tvQrCode.setText(p.getTesteQrCode());
            tvLat.setText(String.valueOf(p.getTesteLat()));
            tvLng.setText(String.valueOf(p.getTesteLng()));
        }
        return v;
    }


}
