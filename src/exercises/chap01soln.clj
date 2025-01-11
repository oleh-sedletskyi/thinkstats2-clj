(ns exercises.chap01soln
  (:require [tablecloth.api :as tc]
            [data.nsfg :as nsfg]
            [fastmath.stats :as stats]))

;;
;;Select the `birthord` column, print the value counts, and compare to results published in the [codebook](https://ftp.cdc.gov/pub/Health_Statistics/NCHS/Dataset_Documentation/NSFG/Cycle6Codebook-Pregnancy.pdf)
;;
(->> (:birthord (nsfg/read-fem-preg-dataset))
     (frequencies)
     (sort-by first))
;;
;;Select the `prglngth` column, print the value counts, and compare to results published in the [codebook](https://ftp.cdc.gov/pub/Health_Statistics/NCHS/Dataset_Documentation/NSFG/Cycle6Codebook-Pregnancy.pdf)
;;

(->> (:prglngth (nsfg/read-fem-preg-dataset))
     (frequencies)
     (reduce (fn [acc [weeks number]]
               (let [length (cond
                              (< weeks 14) "13 weeks or less"    ; groupping 0-13 weeks
                              (> weeks 26) "27 weeks or longer"  ; groupping more than 27 weeks
                              :else        "14-26 weeks")]       ; groupping 14-26 weeks
                 (update acc length (fnil + 0) number)))         ; fnil is for the first element, so its value `nil` is replaced with `0`
             {})
     (sort-by first))

;;
;;To compute the mean of a column, you can invoke the mean method from the `stats` library. For example, here is the mean `birthweight` in pounds `totalwgt-lb`:
(->> (:totalwgt-lb (nsfg/read-fem-preg-dataset))
     (take 20) ;; TODO: Remove it
     (filter some?)
     (stats/mean))
;;
;;Create a new column named `totalwgt-kg` that contains birth weight in kilograms. Compute its mean.
;;
(let [weights (-> (nsfg/read-fem-preg-dataset)
                  (tc/map-columns :totalwgt-kg
                                  [:totalwgt-lb]
                                  (fn [weight-lb]
                                    (when weight-lb
                                      (-> (/ weight-lb 2.205)))))
                  (:totalwgt-kg))
      mean    (->> weights
                   (filter some?)
                   (stats/mean))]
  mean)

;;`nsfg.py` also provides `ReadFemResp`, which reads the female respondents file and returns a Dataset. Tablecloth `head` function returns first `5` rows of the Dataset:
(tc/head (nsfg/read-fem-resp-dataset))
;;
;;Select the `age-r` column from `resp` and print the value counts. How old are the youngest and oldest respondents?
;;
(-> (nsfg/read-fem-resp-dataset)
    (tc/group-by [:age-r])
    (tc/aggregate {:count tc/row-count})
    (tc/order-by [:age-r]))

;;As we can see now, the youngest respondents are `15` and the oldest `44`.
;;
;;We can use the `caseid` to match up rows from `resp` and `preg`. For example, we can select the row from `resp` for `caseid` 2298 like this:
;;
;;```
;;resp[resp.caseid==2298]
;;```
(-> (nsfg/read-fem-resp-dataset)
    (tc/select-rows (fn [row]
                      (= (:caseid row) "2298")))
    :age-r
    first)

;;preg[preg.caseid==2298]
;;
(-> (nsfg/read-fem-preg-dataset)
    (tc/select-rows #(= (:caseid %) "2298"))
    (tc/select-columns [:pregordr :totalwgt-lb]))

;;How old is the respondent with `caseid` 1?
;;
;;
;;What are the pregnancy lengths for the respondent with `caseid` 2298?
;;
;;What was the `birthweight` of the first baby born to the respondent with `caseid` 5013?
