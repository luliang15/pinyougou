package com.pinyougoou;
import java.util.List;
import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbUser;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbUser> findPage(int pageNum, int pageSize, TbUser user);
	
	
	/**
	 * 增加
	*/
	public void add(TbUser user);
	
	
	/**
	 * 修改
	 */
	public void update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
