# -*- coding: utf-8 -*-
# this file is released under public domain and you can use without limitations

#########################################################################
## This is a sample controller
## - index is the default action of any application
## - user is required for authentication and authorization
## - download is for downloading files uploaded in the db (does streaming)
#########################################################################

def index():
    """
    example action using the internationalization operator T and flash
    rendered by views/default/index.html or views/generic.html

    if you need a simple wiki simply replace the two lines below with:
    return auth.wiki()
    """
    response.flash = "%r" % auth.is_logged_in()

    return dict()


def login():
    response.flash = T("Hello ")
    return dict(message=T('Welcome to web2py!'))


@auth.requires_login()
def multi_choice():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Alternativa3': request.vars.Alternativa3,
                 'Alternativa2': request.vars.Alternativa2,
                 'Alternativa1': request.vars.Alternativa1,
                 'Resposta': request.vars.Resposta,
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "multi-choice").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()

@auth.requires_login()
def multi_choice2():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Alternativa3': {request.vars.Alternativa3: request.vars.Correta3 or "0"},
                 'Alternativa2': {request.vars.Alternativa2: request.vars.Correta2 or "0"},
                 'Alternativa1': {request.vars.Alternativa1: request.vars.Correta1 or "0"},
                 'Alternativa0': {request.vars.Alternativa0: request.vars.Correta0 or "0"},
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "multi-choice").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'Atividade Salva com Sucesso'

    return dict()

@auth.requires_login()
def complete():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Palavra': request.vars.Pergunta,
                 'Resposta': request.vars.Resposta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "complete").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        print(form.vars)
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()
# @auth.requires_membership('bercario_admin')
# def cadastro_responsavel():
#     form = SQLFORM(db.pais)
#     if form.validate():
#         form.vars.p_senha = "".join([chr(randrange(48, 122)) for x in xrange(10)])
#         form.vars.id = db.pais.insert(**dict(form.vars))
#         user_id =db.auth_user.insert(**dict(first_name=form.vars.p_nome, last_name="outrem", email='a@gg.com', username = form.vars.p_cpf, password=form.vars.p_senha))
#         auth.add_membership(auth.id_group('bercario_admin'), user_id)
#         response.flash = 'record inserted'
#     return dict(form=form)

@auth.requires_login()
def reports():
    return dict()

def create_account():
    return dict(form=auth.register())



def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()


