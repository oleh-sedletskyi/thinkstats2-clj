(ns exercises.chap01ex
  (:require [tablecloth.api :as tc]
            [data.nsfg :as nsfg]
            [fastmath.stats :as stats]))

;;Some cells are already filled in, and you should execute them. Other cells give you instructions for exercises you should try.
;;
;;A solution to this exercise is in `chap01soln.clj`
;;
;;Select the `birthord` column, print the value counts, and compare to results published in the [codebook](https://ftp.cdc.gov/pub/Health_Statistics/NCHS/Dataset_Documentation/NSFG/Cycle6Codebook-Pregnancy.pdf).
;;
;;`<input type="text" id="birthord" name="name"/>`
;;
;;Select the `prglngth` column, print the value counts, and compare to results published in the [codebook](https://ftp.cdc.gov/pub/Health_Statistics/NCHS/Dataset_Documentation/NSFG/Cycle6Codebook-Pregnancy.pdf).
;;You may need to group the results as `13 WEEKS OR LESS`, `14-26 WEEKS` and `27 WEEKS OR LONGER` to compare it with results in the codebook.
;;
;;`<input type="text" id="prglngth" name="name"/>`
;;
;;To compute the mean of a column, you can invoke the mean method on a Series. For example, here is the mean birthweight in pounds `totalwgt-lb`:
;;
;;`<input type="text" id="prglngth" value="stats/mean" />`
;;
;;Create a new column named `totalwgt-kg` that contains birth weight in kilograms. Compute its mean.
;;
;;`<input type="text" id="totalwgt-kg" />`
;;
;;`nsfg.py` also provides `ReadFemResp`, which reads the female respondents file and returns a DataFrame:
;;
(tc/column-names (nsfg/read-fem-resp-dataset))
;;
;;
;;Select the `age_r` column from `resp` and print the value counts. How old are the youngest and oldest respondents?
