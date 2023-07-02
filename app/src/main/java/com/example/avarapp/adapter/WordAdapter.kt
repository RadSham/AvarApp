package com.example.avarapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.avarapp.activity.DictionaryActivity
import com.example.avarapp.databinding.WordListItemBinding
import com.example.avarapp.dialog.WordDialog
import com.example.avarapp.model.Word
import com.example.avarapp.util.DiffUtilHelper

class WordAdapter(val act: DictionaryActivity, var adapterWordArray: ArrayList<Word>) :
    RecyclerView.Adapter<WordAdapter.WordHolder>() {

    private val dialog = WordDialog()
    val langList = mapOf("a" to "avname")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val binding =
            WordListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordHolder(act,binding)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.setData(adapterWordArray[position])
        holder.itemView.setOnClickListener {
            dialog.showDialog(act, adapterWordArray[position])
        }
    }

    override fun getItemCount(): Int {
        return adapterWordArray.size
    }

    fun updateAdapter(listWords: List<Word>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilHelper(adapterWordArray, listWords))
        diffResult.dispatchUpdatesTo(this)
        adapterWordArray.clear()
        adapterWordArray.addAll(listWords)
    }

    class WordHolder(
        private val activity: DictionaryActivity,
        private val binding: WordListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(word: Word) = with(binding) {
            firstWord.text = getWordFromLanguage(activity.selectedFirstLanguage, word)
            secondWord.text = getWordFromLanguage(activity.selectedSecondLanguage, word)
            avexample.text = word.avexample
        }

        private fun getWordFromLanguage(lang: String, word: Word): String {
            var tempWordText = ""
            when (lang) {
                "Авар мацI" -> tempWordText = word.avname
                "Русский язык" -> tempWordText = word.rusname
                "Turkce" -> tempWordText = word.trname
                "English" -> tempWordText = word.enname
            }
            return tempWordText
        }
    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<Word>) {
        adapterWordArray = filterlist;
        notifyDataSetChanged();
    }

}