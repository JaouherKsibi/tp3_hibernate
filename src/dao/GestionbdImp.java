package dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import model.Categorie;
import model.LignePannier;
import model.Pannier;
import model.Produit;
import model.User;

public class GestionbdImp implements Gestionbd{
	Session session; 
	public GestionbdImp (){
		SessionFactory sessionfactory;
		ServiceRegistry serviceregistry=new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metadata=new MetadataSources(serviceregistry).getMetadataBuilder().build();
		sessionfactory=metadata.getSessionFactoryBuilder().build();
		session=sessionfactory.openSession();
		
		
	} 	
	public User getUserById(int id)
	{
		return this.session.load(User.class, id);
	}
	public User getUserByLoginAndPwd(String login,String pwd)
	{
		User le=null;
		Query q = (Query) session.createQuery("select e from User e where e.adresse_email=:adr and e.mdp=:mdp1 ");
		q.setParameter("adr", login);
		q.setParameter("mdp1", pwd);
		 le=(User) q.getSingleResult();
		return le;
		/*User u=null;
		try {
		String sql = "Select d from User d Where d.adresse_email=:email and d.mdp=:mdp";
		Query<User> query = this.session.createQuery(sql);
		query.setParameter("email", login);
		query.setParameter("mdp", pwd);
		User liv=query.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		/*Query q = em.createNativeQuery("select * from User where adresse_email=? and mdp=?", User.class);
		q.setParameter(1,login);
		q.setParameter(2, pwd);
		try {
			u=(User)q.getSingleResult();
		}
		catch (Exception e){
			return null;
		}*/
		
		
	}
	public void addUser(User e)
	{
		this.session.getTransaction().begin();
		this.session.save(e);
		this.session.getTransaction().commit();
	}
	public void deleteUser(int id)
	{
		User lv = this.session.load(User.class, id);
		this.session.getTransaction().begin();
		this.session.delete(lv);
		this.session.getTransaction().commit();
	}
	public void setUserName(int id,String name)
	{
		this.session.getTransaction().begin();
		User e1 = this.session.load(User.class, id);
		e1.setNom(name);
		this.session.merge(e1);
		this.session.getTransaction().commit();
	}
	public List<Categorie> getAllCategories(){
		
		Query<Categorie> query = this.session.createQuery("Select e from Categorie e ");
		List<Categorie> le = query.getResultList();
		
		/*Query q = this.session.createNativeQuery("select * from Categorie ", Categorie.class);
		Query q = em.createQuery("SELECT e FROM Categorie e");
		List<Categorie> le=q.getResultList();*/
		return le;
	}
	public void addProduit(Produit p)
	{
		this.session.getTransaction().begin();
		this.session.save(p);
		this.session.getTransaction().commit();
	}
	public Categorie getCategoryById(int id)
	{
		
		Categorie u=this.session.load(Categorie.class, id);/*
		Query q = em.createNativeQuery("select * from Categorie where id_catgorie= ? ", Categorie.class);
		q.setParameter(1,id);*/
		/*try {
			u=(Categorie)q.getSingleResult();
		}
		catch (Exception e){
			return null;
		}*/
		
		return u;
	}
	public void addCategorie(Categorie c) {
		this.session.getTransaction().begin();
		this.session.save(c);
		this.session.getTransaction().commit();
	}
	public List<Produit> getAllProduit()
	{
		Query<Produit> q = this.session.createQuery("select e from Produit e ", Produit.class);
		List<Produit> le=q.getResultList();
		return le;
	}
	public List<Produit> getAllProduitByIdCategorie(int id)
	{
		Query<Produit> q = this.session.createQuery("select e from Produit e where e.id_categorie=id", Produit.class);
		q.setParameter("id",id);
		/*Query q = em.createQuery("SELECT e FROM Categorie e");*/
		List<Produit> le=q.getResultList();				
		return le;
				
	}
	public List<Produit> getAllProduitByName(String name)
	{
		Query<Produit> q = this.session.createQuery("select e from Produit e where e.nom_produit like name", Produit.class);
		q.setParameter("name","%"+name+"%");
		/*Query q = em.createQuery("SELECT e FROM Categorie e");*/
		List<Produit> le=q.getResultList();				
		return le;
	}
	public Produit getProduitById(int id) {
		return this.session.load(Produit.class, id);/*****************************************/
		
		 
	}
	public void addquantite(Produit p,int q)
	{
		this.session.getTransaction().begin();
		Produit p1 = this.session.load(Produit.class, p.getId_produit());
		p1.setQuantite(q);
		this.session.merge(p1);
		this.session.getTransaction().commit();
	}
	public void addremise(Produit produitById, float parseInt) {
		this.session.getTransaction().begin();
		Produit p1 = this.session.load(Produit.class, produitById.getId_produit());
		p1.setRemise(parseInt);
		this.session.merge(p1);
		this.session.getTransaction().commit();
	}
	public void addPannier(Pannier p)
	{
		this.session.getTransaction().begin();
		this.session.save(p);
		this.session.getTransaction().commit();
	}
	public int getDernierPannier()
	{
		Query q = this.session.createQuery("select max(id_categorie) from Pannier ", Pannier.class);
		int le=q.getFirstResult();				
		return le;

	}
	public void remplirPannier(Pannier p,float prix) {
		this.session.getTransaction().begin();
		Pannier p1 = this.session.find(Pannier.class, p.getId_pannier());
		p1.setNb_prod(p1.getNb_prod()+1);
		p1.setSomme(p1.getSomme()+prix);
		this.session.merge(p1);
		this.session.getTransaction().commit();
	}
	public List<LignePannier> getAllListePannier(){
		Query<LignePannier> q = this.session.createQuery("select e from LignePannier e", LignePannier.class);
		List<LignePannier> le=q.getResultList();
		return le;
	}
	public int getDerniereLigne()
	{
		
		int le=1;
		for (LignePannier i: this.getAllListePannier()) {
			if(i.getId_ligne()>le) {
				le=i.getId_ligne();
			}
		} 				
		return le;
	}
	public void addLignePannier(LignePannier l) {
		this.session.getTransaction().begin();
		this.session.save(l);
		this.session.getTransaction().commit();
	}
	public void decrementerQuantiteProduit(Produit p) {
		this.session.getTransaction().begin();
		Produit p1 = this.session.load(Produit.class, p.getId_produit());
		p1.setQuantite(p.getQuantite()-1);
		this.session.merge(p1);
		this.session.getTransaction().commit();
		
	}
	public void changerquantite(Produit p,int qt) {
		Produit p1 = this.session.load(Produit.class, p.getId_produit());
		p1.setQuantite(qt);
		this.session.getTransaction().begin();
		this.session.merge(p1);
		this.session.getTransaction().commit();
	}
	public List<LignePannier> getAllLigneByIdPannier(int id)
	{
		Query<LignePannier> q = this.session.createQuery("select e from LignePannier e where e.id_pannier=:id ", LignePannier.class);
		q.setParameter("id",id);
		List<LignePannier> le=q.getResultList();
		return le;
	}
	public void decrimenter_pannier(Pannier p,float prix) {
		this.session.getTransaction().begin();
		Pannier p1 = this.session.load(Pannier.class, p.getId_pannier());
		p1.setNb_prod(p1.getNb_prod()-1);
		p1.setSomme(p1.getSomme()-prix);
		this.session.merge(p1);
		this.session.getTransaction().commit();	
	}
	public void deleteLigne(int id_ligne) {
		LignePannier e = this.session.load(LignePannier.class, id_ligne);
		Pannier p=this.session.load(Pannier.class,e.getPannier().getId_pannier());
		decrimenter_pannier(p, e.getPrixtot());
		this.session.getTransaction().begin();
		
		this.session.delete(e);
		this.session.getTransaction().commit();
	}
}
