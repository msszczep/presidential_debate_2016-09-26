(def path
  "/your/path/goes/here/")

(def stop-words
  (-> (slurp (str path "english_stop_words.txt"))
      (clojure.string/split-lines)
      set))

; get count of words
(->> (remove (partial = "")
       (-> (slurp (str path "trump_text.txt"))
           (clojure.string/replace #"[\n\,\"\.\-\—\:?]" " ")
           (clojure.string/lower-case)
           (clojure.string/split #" ")))
     (remove stop-words)
     set
     count)


(def vowels #{"AA" "AH" "EH" "IH" "OW" "UH" "AE" "AO"
              "AY" "IY" "ER" "EY" "AW" "OY" "UW"})


;; using the CMU Pronunciation Dictionary:
;; http://svn.code.sf.net/p/cmusphinx/code/trunk/cmudict/cmudict-0.7b

(def word-to-syllable-count-map
  (->> (slurp "cmudict-0.7b.txt")
       clojure.string/split-lines
       (map #(clojure.string/replace-first % #" " ""))
       (map #(clojure.string/split % #" " 2))
       (map (juxt first #(clojure.string/replace (second %) #"\d" "")))
       (map (juxt first #(clojure.string/split (second %) #" ")))
       (map (juxt first #(filter vowels (second %))))
       (map (juxt first #(count (second %))))
       (into {})))


; get number of syllables
(->> (remove (partial = "")
       (-> (slurp (str path "clinton_text.txt"))
           (clojure.string/replace #"[\n\,\"\.\-\—\:\?]" " ")
           (clojure.string/lower-case)
           (clojure.string/split #" ")))
     (remove stop-words)
     (map clojure.string/upper-case)
     (map word-to-syllable-count-map)
     (remove nil?)
     (reduce +))


