package ifpr.dispositivosmoveis.atividade5.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import ifpr.dispositivosmoveis.atividade5.R
import ifpr.dispositivosmoveis.atividade5.database.AppDatabase
import ifpr.dispositivosmoveis.atividade5.database.dao.RecordDAO
import ifpr.dispositivosmoveis.atividade5.models.Record
import kotlinx.android.synthetic.main.fragment_choose_recipient.view.*
import kotlinx.android.synthetic.main.item_record.view.*
import kotlinx.android.synthetic.main.item_record.view.tvDate
import kotlinx.android.synthetic.main.item_record.view.tvPerson
import kotlinx.android.synthetic.main.item_record.view.tvValue
import kotlinx.android.synthetic.main.item_record.view.tvType
import kotlinx.android.synthetic.main.item_record.view.imageView2

import kotlinx.android.synthetic.main.item_record.view.tvMoneyPrefix

import kotlinx.android.synthetic.main.item_record_edit.view.*
import java.security.AccessController.getContext

import java.text.SimpleDateFormat

class RecordAdapter(context: Context, userId: Long) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private val dao: RecordDAO
    private val records: MutableList<Record>
    private var recordEditing: Record? = null

    init {
        dao =  AppDatabase.getInstance(context).recordDAO()
        records = dao.getAll(userId).toMutableList()
    }

    fun edit(record: Record) {
        recordEditing = record
        val position = records.indexOf(record)
        notifyItemChanged(position)
    }

    fun save(record: Record) {
        dao.update(record)
        val position = records.indexOf(record)
        recordEditing = null
        notifyItemChanged(position)
    }

    override fun getItemCount() = records.size


    override fun getItemViewType(position: Int): Int {
        val record = records[position]

        if (record == recordEditing) {
            return R.layout.item_record_edit
        }

        return R.layout.item_record;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = records[position]
        holder.fillView(person)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(record: Record) {
            itemView.tvValue.setText(record.value.toString())
            itemView.tvPerson.setText(record.person.toString())

            itemView.tvDate.setText(record.getFormattedDate())

            if (record.value < 0) {
                itemView.tvValue.setTextColor(Color.parseColor("#F44336"))
                itemView.tvMoneyPrefix.setTextColor(Color.parseColor("#F44336"))
                itemView.imageView2.setColorFilter(Color.parseColor("#F44336"));
            }

            if (recordEditing == record) {
                itemView.editTextNote.setText(record.remarks)

                itemView.btnEditRecord.setOnClickListener{
                    record.remarks = itemView.editTextNote.text.toString();
                    save(record)
                }
            }

            itemView.setOnClickListener {
                edit(record)
            }
        }
    }
}