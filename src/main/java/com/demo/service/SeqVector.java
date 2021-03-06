package com.demo.service;

import com.demo.dao.QAEntity;
import com.demo.dao.SimilarResultDao;
import com.hankcs.hanlp.mining.word2vec.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author d-xsj
 * mail coder_xushijia@126.com
 * date 08/03/2018
 * Description:
 */
@Service
public class SeqVector {
    @Autowired
    SimilarResultDao similarResultDao;

   @Autowired
    DocVector docVector;




    public String getSeqVector(int num,String queue) throws Exception {

        StringBuffer seqVector =new StringBuffer();
        Vector vector = docVector.query(queue);
                if (vector==null){
                    System.out.println(num);
                    return "";
                };


        if (vector.elementArray==null){
            System.out.println("elementArray is null");
        }

        for (float f:vector.elementArray) {
            seqVector.append(String.valueOf(f)+"\t");
        }
        System.out.println("<----"+num+"is ok---->");
        return seqVector.toString();

    }


    public void insertVector() throws Exception {
        int[] reInt = similarResultDao.getNum();

        int limit = reInt.length;
        for (int i=0;i<limit;i++){
            QAEntity question = similarResultDao.getQAEntity(reInt[i]);
            String queue = question.getQuestionContent().trim()+question.getQuestionTitle().trim();

            String vector = getSeqVector(question.getNum(),queue);
           // System.out.println(vector);
            similarResultDao.setVector(question.getNum(),vector);

        }
    }
}
