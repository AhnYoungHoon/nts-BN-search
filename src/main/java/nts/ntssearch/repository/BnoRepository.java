package nts.ntssearch.repository;

import nts.ntssearch.domain.closedNum;
import nts.ntssearch.domain.presentNum;

import java.util.List;


public interface BnoRepository {
    public void savePresent(String b_no);

    public void saveClosed(String b_no);

    List<presentNum> findPresent();

    List<closedNum> findClosed();
}
